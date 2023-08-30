package edu.escuelaing.arep.app;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

public class HttpServer {
    
    /** 
     * metodo principal que determina el puerto por el que se hara la conexion, creara el pool de hilos y creara instancias del generador de respuestas para que los hilos las ejecuten.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            ResponsesCreator responsesCreator = new ResponsesCreator(clientSocket);
            threadPool.execute(responsesCreator);
        }
        serverSocket.close();
    }

}