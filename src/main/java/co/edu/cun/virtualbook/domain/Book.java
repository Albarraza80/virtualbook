package co.edu.cun.virtualbook.domain;

import java.util.List;

/*Limpiar el formulario 
  quitar las filas vacias 
    agregar el boton de borrar
    
*/

public record Book(int id,
                   String title,
                   String genre,
                   Integer pages,
                   int publicYear,
                   String editorial,
                   List<Author> authorList){
}
