package converters;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Getter
@Log4j2
public  class DataParser  implements  Closeable {

   protected String extension;
   protected String rootLocation;
   protected  String name;
   protected  int bufferSize= 8 * 1024;
   private IOException exception= null;
   public void  saveToFile(InputStream stream ) {
      try (OutputStream outStream = new FileOutputStream(getFullPath(),false)) {
         byte[] buffer = new byte[bufferSize];
         int bytesRead;
         while ((bytesRead = stream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
         }
      } catch (IOException e) {
         this.exception = e;
      }
   }

    private String getFullPath() {
       return  rootLocation + name + extension;
    }

    public void  saveToFile(byte[] bytes ) {
        try (PrintStream printStream= new PrintStream(rootLocation +extension)){
            printStream.write(bytes);
        } catch (IOException e) {
            this.exception = e;
        }
    }

   public  void writeBinary(InputStream inputStream) {
      java.nio.file.Path filePath = Paths.get(rootLocation +extension);

      // Open a channel in write mode on your file.
      try (WritableByteChannel channel = Files.newByteChannel(filePath, StandardOpenOption.CREATE)) {
         // Allocate a new buffer.
            ByteBuffer buf = ByteBuffer.allocate(bufferSize * 10 );
            buf.put(new byte[inputStream.available()] );
            channel.write(buf);  // Write your buffer's data.
         } catch (IOException e) {
          this.exception = e;
        }
      }

    @Override
    public void close() throws IOException {
        if(exception != null) {
            throw new IOException(exception);
        }
        else {
            log.debug("{} finished successfully" , this.getClass().getName());
        }
    }
}

