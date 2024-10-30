package co.edu.cun.virtualbook.config;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ApplicationConfig{
    private static ApplicationConfig instace;

    private Map<String, Object> config;

    private ApplicationConfig( )
        throws IOException{
        Yaml yaml = new Yaml();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream( "properties.yml" );
        if(inputStream == null){
            throw new RuntimeException();
        }
        config = yaml.load( inputStream );
        inputStream.close();
    }

    public static ApplicationConfig getInstance ()
        throws IOException{

        if(instace == null){
            instace = new ApplicationConfig();
        }
        return instace;
    }

    public String getUrl(){
        Map<String, Object> dbConfig = ( Map<String, Object> ) config.get( "db" );

        return ( String ) dbConfig.get("url");
    }

    public String getUser(){
        Map<String, Object> dbConfig = ( Map<String, Object> ) config.get( "db" );

        return ( String ) dbConfig.get("username");
    }

    public String getPassword(){
        Map<String, Object> dbConfig = ( Map<String, Object> ) config.get( "db" );

        return ( String ) dbConfig.get("password");
    }

}
