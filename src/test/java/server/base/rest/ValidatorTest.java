package server.base.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ValidatorTest{
	private static final Logger log = LogManager.getLogger(ValidatorTest.class);

    Exception e;
    @Test
    public void postgresTestDriver_Work() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e) {
            this.e=e;

        }
        finally {
            Assert.assertTrue(e==null);
        }
    }


    @Test
    public void postgresTestDriver_Fail() {
        e=null;
        try {
            Class.forName("org.postgresql.Driver");
            log.debug("success" );
        } catch(ClassNotFoundException e) {
            this.e=e;
            e.printStackTrace();

        }
        finally {
            Assert.assertTrue(e!=null);
        }
    }

    @Test()
    public void  validatorXML_With_Attribute() throws IOException {
        String xmlString = "<person id=\"1\"> <company> 10  </company>  </person>";
        boolean isValid = validator(xmlString);
        Assert.assertTrue(isValid);
    }

    @Test()
    public void  validatorXML_No_Attribute() throws IOException {
        String xmlString = "<person> <company> 10  </company>  </person>";
        boolean isValid = validator(xmlString);
        Assert.assertTrue(isValid);
    }


    @Test(expected = StringIndexOutOfBoundsException.class)
    public void  validatorXML_Not_valid_More_Than_TwoElements() throws IOException {
        //  String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        String xmlString = "<person> <company> 10  </company> </person> </company>";
        validator(xmlString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void  validatorXML_Less_Than_twice() throws IOException {
        //  String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        String xmlString = "<person> <company> 10  </company> ";
        validator(xmlString);
    }

    @Test(expected = IOException.class)
    public void  validatorXML_Not_valid_order() throws IOException {
        //  String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        String xmlString = "<person> <company> 10  </person> </company>";
        validator(xmlString);
    }

    private  void countOccurrences (String subText, StringBuffer text ){
        int count = 0, fromIndex = 0;

        while ((fromIndex = text.indexOf(subText, fromIndex)) != -1 ){
            count++;
            if(count > 2 ) {
                throw  new StringIndexOutOfBoundsException("Illegal occurrence the member  "+ subText + " in the text " + count + " times ");
            }
            fromIndex++;
        }
        if(count < 1 ) {
            throw  new StringIndexOutOfBoundsException("Illegal occurrence the member  "+ subText + " in the text less than two times ");
        }
    }

    private boolean  validator(String xml) throws IOException {
             int iPos=0;
             String element;
             int firstClose= xml.indexOf("</");
             Map<String,Integer[]> elementsMap= new HashMap<>();
             while (true) {
                int iStart = xml.indexOf("<",iPos);
                Integer iEnd = getLastLocation(xml, firstClose, iStart);
                if (iEnd == null) break;
                element = xml.substring(iStart+1, iEnd);
                countOccurrences(element, new StringBuffer(xml));
                element= cleanAttributes(element) ;
                Integer[] location = new Integer[] {iStart,xml.lastIndexOf("</"+ element)};
                elementsMap.put(element,location) ;
                log.debug("element is {}",element);
                iStart = xml.indexOf(element, iEnd + 1);
                if (iStart == -1) {
                    throw new IllegalArgumentException(" wrong xml format node " + element + "doesn't exist");
                }
                iEnd= iStart+ element.length();
                if (xml.substring(iStart - 2, iStart).equals("</") && xml.charAt(iEnd) == '>') {
                    iPos = xml.indexOf(element)+1+element.length();
                }
            }

             int prevStartPost=-1;
             int prevEndPos= xml.length();
             for(String key: elementsMap.keySet()){
                 int iStart = elementsMap.get(key)[0];
                 int iEnd = elementsMap.get(key)[1];
                 if(iStart > prevStartPost && iEnd < prevEndPos) {
                     prevStartPost=iStart;
                     prevEndPos=iEnd;
                 }
                 else {
                     throw  new IOException(" you fields order my be incorrect for key  "+ key );
                 }
             }
             log.debug("success");
             return true;
    }

    private String cleanAttributes(String text) {
        int attributePosition= text.indexOf(' ');
        if(attributePosition!= -1 ) {
            return text.substring(0,attributePosition);
        }
        log.debug("text is {}",text);
        return text;
    }

    private Integer getLastLocation(String xmlString, int firstClose, int iStart) {
        if(iStart > firstClose -1) {
            return null;
        }
        if (iStart == -1) {
            throw new IllegalArgumentException(" wrong xml format");
        }

        int iEnd = xmlString.indexOf(">", iStart + 1);
        if (iEnd == -1) {
            throw new IllegalArgumentException(" wrong xml format");
        }
        return iEnd;
    }
}