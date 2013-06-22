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
#include <stdbool.h>

#define PORT 3550 /* El puerto que será abierto */
#define BACKLOG 2 /* El número de conexiones permitidas */

void selectionMethod( int sock, char *rfc, char i )
{
	printf("\nOption:%c", i);
	switch( i ) 
	{
		case 's':
			searchRFC( sock, rfc );
			break;
		case 'c' :
			cancelLoan( sock, rfc  );
			break;
		case 'a' :
			break;
	}
}

void searchRFC( int sock, char *rfc )
{
	int n, x;
	x = strlen(rfc);
	printf( "\nRFC:%s", rfc );
	printf( "\nLength: %d", x );
	
	FILE *f;
	f = fopen("Loans.txt", "r");
	char line[100];
	char word[x];
	char *p;
	bool found = false;

	while( fgets( line, 100, f ) != NULL )
	{
		p = line + 4;		
		strncpy( word, p, 10 );
		
		if( strncmp( word, rfc, 10 ) == 0 )
		{
			send( sock, line, strlen(line), 0 );
			printf( "\nLine:%s", line );
			found = true;
		}
    }
	if( found == false )
	{
		send( sock, rfc, x, 0 );
		send( sock, "\t>>\tNot Found!", 17, 0 );
		printf("\n%s Not Found.", rfc);
	}
	for( n = 0; n < 10; n ++)
	{
		word[n] = '0';
	}
	p = '\0';
	fclose( f );
}

void cancelLoan( int sock, char *rfc )
{
	int n, x;
	x = strlen(rfc);
	printf( "\nRFC:%s", rfc );
	printf( "\nLength: %d", x );
	
	FILE *f;
	
	if( ( f = fopen("Loans.txt", "r") ) == NULL )
	{
		printf( "Trouble opening file\n" );
	}
	else
	{
		char line[100];
		char word[x];
		char *p;
		bool found = false;

		while( fgets( line, 100, f ) != NULL )
		{
			p = line + 4;		
			strncpy( word, p, 10 );
		
			if( strncmp( word, rfc, 10 ) == 0 )
			{
				send( sock, line, strlen(line), 0 );
				printf( "\nLine:%s", line );
				found = true;
				
				char *str;
				str = strrchr( line, 'Y');
				printf ("\nFound %s", str);
				
				char *id;
				id = strstr( str, "|" ) + 1;
				int i = 0;
				printf("ID:%s", id);
				
			/*	while( str[i]!='\0' )
				{
					i++;
				}
			*/
			}
		}
		if( found == false )
		{
			send( sock, rfc, x, 0 );
			send( sock, "\t>>\tNot Found!", 17, 0 );
			printf("\n%s Not Found.", rfc);
		}
		fclose( f );
	}
}

void doprocessing( int sock )
{
    int x;
    char buffer[256];

    memset( &(buffer), '0', 256 );
    int recvMsgSize;
	recvMsgSize = recv( sock, buffer, 256, 0 );
	char rfc[recvMsgSize -1];
	
	for( x = 0; x < recvMsgSize -1; x++)
	{
		rfc[x] = buffer[x+1];
	}
	
    /* Receive message from client */
    /* if( ( recvMsgSize = recv(sock, buffer, 256, 0) ) < 0 )
	{
        perror("*ERROR reading to socket");
	} */

	/* Send received string and receive again until end of transmission */
	while( recvMsgSize > 0 )      // zero indicates end of transmission
	{
		selectionMethod( sock, rfc, buffer[0] );
			
		/* Echo message back to client */
		if( send( sock, buffer, recvMsgSize, 0 ) != recvMsgSize ) {
			perror("\nERROR writing to socket");
		}

		/* See if there is more data to receive */
		if( ( recvMsgSize = recv( sock, buffer, 256, 0 ) ) < 0 ) {
			perror("\nERROR reading to socket");
		}
	}
	closesocket(sock);    /* Close client socket */
}

BOOL initW32() 
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
	initW32(); /* Necesaria para compilar en Windows */ 
	 	
	int fd, fd2; /* los descriptores de archivos */

	/* para la información de la dirección del servidor */
	struct sockaddr_in server;

	/* para la información de la dirección del cliente */
	struct sockaddr_in client;

	unsigned int sin_size;

	pid_t pid;

	/* A continuación la llamada a socket() */
	if( ( fd = socket( AF_INET, SOCK_STREAM, 0 ) ) == -1 ) {
      printf("Error en socket()\n");
      exit(-1);
	}

	server.sin_family = AF_INET;

	server.sin_port = htons(PORT);

	server.sin_addr.s_addr = INADDR_ANY;
	/* INADDR_ANY coloca nuestra dirección IP automáticamente */

	//bzero(&(server.sin_zero),8);
	memset( &(server.sin_zero), '0', 8 );
	/* escribimos ceros en el reto de la estructura */


	/* A continuación la llamada a bind() */
	if( bind( fd,(struct sockaddr*) &server, sizeof( struct sockaddr ) ) == -1 ) {
		printf("error en bind() \n");
		exit(-1);
	}

   if( listen(fd, BACKLOG) == -1 ) {  /* llamada a listen() */
      printf("error en listen()\n");
      exit(-1);
   }

   while(1)
   {
      sin_size = sizeof( struct sockaddr_in );
      
	  /* A continuación la llamada a accept() */
      if( ( fd2 = accept( fd, (struct sockaddr *) &client, &sin_size )) == -1 ) {
         printf("error en accept()\n");
         exit(-1);
      }

      printf("Se obtuvo una conexion desde %s\n", inet_ntoa( client.sin_addr ) );
      /* que mostrará la IP del cliente */

      //send( fd2, "Hola, bienvenido Miguel Angel\n", 32, 0 );
      /* que enviará el mensaje de bienvenida al cliente */
      
      doprocessing(fd2);
   } /* end while */
}