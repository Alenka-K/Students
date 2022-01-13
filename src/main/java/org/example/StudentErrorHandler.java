package org.example;



import org.apache.log4j.Logger;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;



public class StudentErrorHandler extends DefaultHandler {


        private Logger logger = Logger.getLogger(StudentErrorHandler.class);


        public void warning(SAXParseException e) {

            logger.warn(e + "-" + e.getMessage());
            System.out.println(e.getMessage());// уведомление о предупреждении
        }
        public void error(SAXParseException e) {
            logger.error(e + "-" + e.getMessage());
            System.out.println(e.getMessage());//уведомление об исправимой ошибке
            System.exit(0);
        }
        public void fatalError(SAXParseException e) {

            logger.fatal(e + " - " + e.getMessage());
            System.out.println(e.getMessage());// уведомление о фатальной ошибке разбора XML.
            System.exit(0);
        }

}
