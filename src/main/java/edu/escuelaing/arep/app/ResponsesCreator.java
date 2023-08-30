package edu.escuelaing.arep.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResponsesCreator implements Runnable{

    private final Socket clientSocket;

    public ResponsesCreator(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            responseCreator();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    /**
     * metodo que genera los buffers y writters con los flujos de bytes y crean la respuesta de acuerdo a la solicitud recibida
     * @throws IOException
     */
    public void responseCreator() throws IOException{
        
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            boolean firstReadLine = true;
            String request = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (firstReadLine == true) {
                    request = inputLine;
                    firstReadLine = false;
                }
                if (!in.ready()) {
                    break;
                }
            }
            createResponse(request, out, clientSocket.getOutputStream());
            out.close();
            in.close();
            clientSocket.close();
    }

    /** 
     * este metodo selecciona lo requerido de la solicitud para obtener la direccion completa del archivo el cual se necesita retornar en el browser. Este hara las acciones indicadas con 
     * los flujos de bytes de acuerdo al tipo de archivo que se solicite. 
     * @param request String compuesto por la request generada al realizar la solicitud en el buscador
     * @param out es un PrintWriter que te permite escribir datos en el flujo de salida del socket del cliente
     * @param outv2 flujo de salida de bytes
     * @throws IOException
     */
    public static void createResponse(String request, PrintWriter out, OutputStream outv2) throws IOException {
        System.out.println("request to interpret: " + request);
        if (request.equals("")) {
            return;
        }
        String[] tokenizedRquest = request.split(" ");
        String path = tokenizedRquest[1];

        Path file = Paths.get("./src/main/java/edu/escuelaing/arep/app/resources/public" + path);
        if (path.contains(".jpg") || path.contains(".png")) {
            try {
                Path imagePath = Paths.get("./src/main/java/edu/escuelaing/arep/app/resources/public" + path);
                byte[] imageBytes = Files.readAllBytes(imagePath);
                String defaultHeader = "HTTP/1.1 200 OK\r\n"
                        + "content-Type: image/jpg\r\n"
                        + "content-Length: " + imageBytes.length + "\r\n"
                        + "\r\n";
                outv2.write(defaultHeader.getBytes());
                outv2.write(imageBytes);
            } catch (IOException e) {
                out.println(notPageFound());
            }
        }else if (path.contains(".js")) {
            try {
                Path jsFile = Paths.get("./src/main/java/edu/escuelaing/arep/app/resources/public" + path);
                String Content = new String(Files.readAllBytes(jsFile), Charset.forName("UTF-8"));

                String defaultHeader = "HTTP/1.1 200 OK\r\n" + "content-Type: text/javascript\r\n" + "\r\n";
                out.println(defaultHeader);
                out.println(Content);
            } catch (IOException e) {
                out.println(notPageFound());
            }
        }else if (path.contains(".html")) {
            Charset charset = Charset.forName("UTF-8");
            try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
                String defaultHeader = "HTTP/1.1 200 OK\r\n"
                    + "content-Type: text/html\r\n"
                    + "\r\n";
            out.println(defaultHeader);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    out.println(line);
                }
            } catch (IOException x) {
                out.println(notPageFound());
            }
        }else if(path.contains(".css")){
            Charset charset = Charset.forName("UTF-8");
            try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
                String defaultHeader = "HTTP/1.1 200 OK\r\n"
                    + "content-Type: text/css\r\n"
                    + "\r\n";
            out.println(defaultHeader);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    out.println(line);
                }
            } catch (IOException x) {
                out.println(notPageFound());
            }
        }else{
            out.println(notPageFound());
        }
    }
    
    /** 
     * @returndefaul header cuando no se encuentra el archivo
     */
    public static String notPageFound(){
        String defaultHeader = "HTTP/1.1 404 Not Found\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset=\"UTF-8\">"
                    + "<title>Title of the document</title>\n" + "</head>"
                    + "<body>"
                    + "Error 404"
                    + "</body>"
                    + "</html>";
        return defaultHeader;
    }
}
