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
#define BACKLOG 2 /* El número de conexiones permitidas */
#define DEFAULT_BUFFER_SIZE 256 /* Tamanio del buffer por default */

void doprocessing (int sock)
{
    char buffer[DEFAULT_BUFFER_SIZE];

    memset(&(buffer), '0', DEFAULT_BUFFER_SIZE);
    int recvMsgSize;
    int accumulated = 0; /* Contador de los bytes recuperados de la conexion */
    
    /* Send received string and receive again until end of transmission */
    
    do {
        if ( accumulated == DEFAULT_BUFFER_SIZE) 
        {
            // TODO: Procesar el mensaje
            // TODO: Generar respuesta del mensaje, es importante terminar el mensaje con 0x03
            //       para que el cliente sepa en donde termina.            
            send(sock, b ,sizeof(b),0); // En este caso como se está regresando el mismo mensaje ya trae ese caracter.
        }

        /* See if there is more data to receive */
        if ((recvMsgSize = recv(sock, buffer, DEFAULT_BUFFER_SIZE, 0)) < 0){
            perror("ERROR reading to socket");
        }
        
        accumulated += recvMsgSize; 
            
    } while (recvMsgSize > 0);      /* zero indicates end of transmission */

    printf(buffer);
    printf("pase por aca");
    closesocket(sock);    /* Close client socket */
}

BOOL init_winsock2() 
{
		WSADATA wsaData;
		WORD version;
		int error;
		
		version = MAKEWORD( 2, 0 );
		
		error = WSAStartup( version, &wsaData );
		
		/* check for error */
		if ( error != 0 )
		{
		    /* error occured */
		    return FALSE;
		}
		
		/* check for correct version */
		if ( LOBYTE( wsaData.wVersion ) != 2 ||
		     HIBYTE( wsaData.wVersion ) != 0 )
		{
		    /* incorrect WinSock version */
		    WSACleanup();
		    return FALSE;
		}	
}

int main()
{

   init_winsock2(); /* Necesaria para compilar en Windows */ 
	 	
   int fd, fd2; /* los descriptores de archivos */

   /* para la información de la dirección del servidor */
   struct sockaddr_in server;

   /* para la información de la dirección del cliente */
   struct sockaddr_in client;

   unsigned int sin_size;

   pid_t pid;

   /* A continuación la llamada a socket() */
   if ((fd=socket(AF_INET, SOCK_STREAM, 0)) == -1 ) {
      printf("error en socket()\n");
      exit(-1);
   }

   server.sin_family = AF_INET;

   server.sin_port = htons(PORT);

   server.sin_addr.s_addr = INADDR_ANY;
   /* INADDR_ANY coloca nuestra dirección IP automáticamente */

   memset(&(server.sin_zero), '0', 8);
   /* escribimos ceros en el reto de la estructura */


   /* A continuación la llamada a bind() */
   if(bind(fd,(struct sockaddr*)&server, sizeof(struct sockaddr))==-1) {
      printf("error en bind() \n");
      exit(-1);
   }

   if(listen(fd, BACKLOG) == -1) {  /* llamada a listen() */
      printf("error en listen()\n");
      exit(-1);
   }

   while(1) {
      sin_size=sizeof(struct sockaddr_in);
      /* A continuación la llamada a accept() */
      if ((fd2 = accept(fd,(struct sockaddr *)&client, &sin_size))==-1) {
         printf("error en accept()\n");
         exit(-1);
      }

      printf("Se obtuvo una conexión desde %s\n", inet_ntoa(client.sin_addr) );
      /* que mostrará la IP del cliente */
      
      doprocessing(fd2);

   } /* end while */
}
