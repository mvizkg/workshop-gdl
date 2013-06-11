/**
 * Este aplicacion representa a una entidad crediticia la cual
 * concentra todos los creditos otorgados a los clientes de
 * diferentes entidades.
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <winsock2.h>
#include <unistd.h>

#define PORT 3550 /* El puerto que será abierto */
#define ALLOWED_CONNECTIONS 2 /* El número de conexiones permitidas */
#define DEFAULT_BUFFER_SIZE 128 /* Tamanio del buffer por default */

// Prototipos
void process_messages (int sock);

char *rtrim(char *s) {
    char* back = s + (strlen(s)-2);
    while (isspace(*--back));
    *(back+1) = '\0';
    return s;
}

void start_server() {
    init_winsock2(); /* Necesaria para compilar en Windows */

    int server_socket, client_socket; /* los descriptores de archivos */

    /* para la información de la dirección del servidor */
    struct sockaddr_in server;

    /* para la información de la dirección del cliente */
    struct sockaddr_in client;

    unsigned int sin_size;

    /* A continuación la llamada a socket() */
    if ((server_socket=socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) == -1 ) {
        printf("error en socket()\n");
        exit(-1);
    }

    server.sin_family = AF_INET;
    server.sin_port = htons(PORT);
    server.sin_addr.s_addr = INADDR_ANY;  /* INADDR_ANY coloca nuestra dirección IP automáticamente */
    memset(&(server.sin_zero), '0', 8);   /* Llenamos de ceros en el resto de la estructura */

    /* A continuación la llamada a bind() */
    if (bind(server_socket,(struct sockaddr*)&server, sizeof(struct sockaddr))==-1) {
        printf("error en bind() \n");
        exit(-1);
    }

    if (listen(server_socket, ALLOWED_CONNECTIONS) == -1) { /* llamada a listen() */
        printf("error en listen()\n");
        exit(-1);
    }

    while (TRUE) {
        sin_size=sizeof(struct sockaddr_in);

        /* A continuación la llamada a accept() */
        if ((client_socket = accept(server_socket,(struct sockaddr *)&client, &sin_size))==-1) {
            printf("error en accept()\n");
            exit(-1);
        }

        printf("Se obtuvo una conexión desde %s\n", inet_ntoa(client.sin_addr) );
        /* que mostrará la IP del cliente */

        process_messages(client_socket);

    } /* end while */
}

void process_messages (int sock) {
    char buffer[DEFAULT_BUFFER_SIZE];

    memset(&(buffer), '0', DEFAULT_BUFFER_SIZE);
    int recvMsgSize;

    /* Send received string and receive again until end of transmission */

    do {
        /* See if there is more data to receive */
        if ((recvMsgSize = recv(sock, buffer, DEFAULT_BUFFER_SIZE, 0)) < 0) {
            perror("ERROR reading to socket");
            break;
        }

        if (recvMsgSize > 0) {
            // TODO: Determinar el tipo de mensaje antes de procesarlo.
            char loans[2048];
            find_loans(loans, rtrim(buffer));
            send(sock, loans ,sizeof(loans), 0); // En este caso como se está regresando el mismo mensaje ya trae ese caracter.
        }
    } while (recvMsgSize > 0);     /* zero indicates end of transmission */

    closesocket(sock);    /* Close client socket */
}

BOOL init_winsock2() {
    WSADATA wsaData;
    WORD version;
    int error;

    version = MAKEWORD( 2, 0 );

    error = WSAStartup( version, &wsaData );

    /* check for error */
    if ( error != 0 ) {
        /* error occured */
        return FALSE;
    }

    /* check for correct version */
    if ( LOBYTE( wsaData.wVersion ) != 2 ||
            HIBYTE( wsaData.wVersion ) != 0 ) {
        /* incorrect WinSock version */
        WSACleanup();
        return FALSE;
    }
}

int main() {
    char loans[2048];
    //find_loans(loans, "23456ABCDE");
    start_server();
}

/**
 * File management
 */
#define LOAN_RECORD_SIZE 115
#define FIELD_SEPARATOR "|"
#define RFC_FIELD_COLUMN 2

int find_loans(char *loans, char *rfc) {
    printf("%s*-*",rfc);
    FILE * fp;
    char line[LOAN_RECORD_SIZE];
    char *field;
    int field_counter;
    int loan_counter;
    loans[0] = '\0';

    fp = fopen("Loans.txt", "r");
    if (fp == NULL)
        return -1;
    fgets(line, LOAN_RECORD_SIZE, fp); // Skip the header
    while (fgets(line, LOAN_RECORD_SIZE, fp) != NULL) {
        //printf("%s*\n", line);
        char original_line[LOAN_RECORD_SIZE];
        strcpy(original_line, line);
        field = strtok(line, FIELD_SEPARATOR);
        field_counter++;
        while (field != NULL) {
            field = strtok (NULL, FIELD_SEPARATOR);
            field_counter++;
            if (field_counter == RFC_FIELD_COLUMN) {
                if (strcmp(rfc,field) == 0) {
                    strcat(loans, "@");  // Add loan separator
                    strcat(loans, original_line);
                }
            }
        }

        field_counter = 0;
    }

    fclose(fp);
    strcat(loans, "#\0");  // Null to delimit the string and char to indicate the end of loan
    loans[strlen(loans)] = 0x03; // End of Text character
    return 0;
}


