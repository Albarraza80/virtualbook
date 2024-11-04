package co.edu.cun.virtualbook.form;

import co.edu.cun.virtualbook.domain.Author;
import co.edu.cun.virtualbook.domain.Book;
import co.edu.cun.virtualbook.domain.Category;
import co.edu.cun.virtualbook.service.AuthorService;
import co.edu.cun.virtualbook.service.BookService;
import co.edu.cun.virtualbook.service.CategoryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private JPanel mainPanel;
    private JTextField bookName;
    private JTextField totalPagesField;
    private JButton saveButton;
    private JTable listBook;
    private JComboBox genreList;
    private JComboBox authorList;
    private JTextField editorialField;
    private JTextField publicYearField;
    private JButton deleteButton;
    private BookService bookService;  // Instancia de BookService
    private CategoryService categoryService;  // Instancia de CategoryService
    private AuthorService authorService;  // Instancia de AuthorService
    private Integer bookSelectedId;

    public Main() throws IOException, SQLException {
        // Inicializar el servicio de libros & categorias
        bookService = new BookService();
        categoryService = new CategoryService();
        authorService = new AuthorService();

        // Crear el modelo de la tabla con las columnas requeridas
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Genre");
        model.addColumn("Total Pages");
        model.addColumn("Editorial");
        model.addColumn("Author (s)");
        model.addColumn("Public Year");

        // Asignar el modelo a la tabla
        listBook.setModel(model);

        // Llenar la tabla al cargar la aplicación
        loadTableData(model);
        // Llenar el JComboBox con las categorías
        loadGenres();
        // Llenar el JComboBox con los autores
        loadAuthors();

        // Agregar un listener para la tabla
        listBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = listBook.getSelectedRow();
                if (row != -1) { // Verificar que se ha seleccionado una fila
                    try {
                        loadBookDataToForm(row);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    deleteButton.setVisible(true);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    bookService.delete(bookSelectedId);
                    clearForm();
                    refreshTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (saveButton.getText().equals("Update")) {
                        updateBook(bookSelectedId);
                    } else {
                        saveBook();
                    }
                    refreshTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        deleteButton.setVisible(false);
    }

    private void loadAuthors() throws SQLException {
        // Obtener todos los autores de AuthorService
        List<Author> authors = authorService.findAll();

        // Agregar cada autor al JComboBox
        for (Author author : authors) {
            authorList.addItem(author.getId() + " - " +author.getName() + " " + author.getLastname());
        }
    }

    private void loadGenres() throws SQLException {
        // Obtener todas las categorías de CategoryService
        List<Category> categories = categoryService.findAll();

        // Agregar cada categoría al JComboBox
        for (Category category : categories) {
            genreList.addItem(category.getName());  // Suponiendo que getName() devuelve el nombre de la categoría
        }
    }

    private void loadTableData(DefaultTableModel model) throws SQLException {
        // Obtener todos los libros del servicio
        List<Book> books = bookService.findAll();

        // Agregar cada libro como una fila en el modelo de la tabla
        for (Book book : books) {
            model.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getGenre(),
                    book.getPages(),
                    book.getEditorial(),
                    book.getAuthorList().stream()
                            .map(author -> author.getName() + " " + author.getLastname() + " - "+ author.getId())
                            .collect(Collectors.joining(", ")),
                    book.getPublicYear()
            });
        }
    }

    private void refreshTable() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) listBook.getModel();
        model.setRowCount(0);  // Limpiar el modelo de la tabla
        loadTableData(model);  // Recargar los datos
    }

    private void saveBook() throws SQLException {
            String title = bookName.getText();
            String genre = genreList.getSelectedItem().toString();
            String editorial = editorialField.getText();
            Integer publicYear = Integer.parseInt(publicYearField.getText());
            Integer totalPages = Integer.parseInt(totalPagesField.getText());

            String author = authorList.getSelectedItem().toString();
            String[] authorData = author.split(" - ");
            Integer authorId = Integer.parseInt(authorData[0]);

            Author authorSelected = authorService.findById(authorId);

            List<Author> authors = new ArrayList<>();
            authors.add(authorSelected);

            Book book = bookService.createBook(
                    title,
                    genre,
                    editorial,
                    publicYear,
                    totalPages,
                    authors // Pasar la lista de autores
            );

            String message = "Libro " + book.getTitle() + " creado exitosamente!";
        JOptionPane.showMessageDialog(null, message);
        clearForm();
    }

    private void updateBook(Integer id) throws SQLException {
        String title = bookName.getText();
        String genre = genreList.getSelectedItem().toString();
        String editorial = editorialField.getText();
        Integer publicYear = Integer.parseInt(publicYearField.getText());
        Integer totalPages = Integer.parseInt(totalPagesField.getText());

        String author = authorList.getSelectedItem().toString();
        String[] authorData = author.split(" - ");
        Integer authorId = Integer.parseInt(authorData[0]);

        Author authorSelected = authorService.findById(authorId);

        List<Author> authors = new ArrayList<>();
        authors.add(authorSelected);

        Book book = bookService.update(
                id,
                title,
                genre,
                editorial,
                publicYear,
                totalPages,
                authors // Pasar la lista de autores
        );

        String message = "Libro " + book.getTitle() + " actualizado exitosamente!";
        JOptionPane.showMessageDialog(null, message);
        clearForm();
    }

    private void clearForm() {
        bookName.setText("");
        genreList.setSelectedIndex(0);
        editorialField.setText("");
        publicYearField.setText("");
        totalPagesField.setText("");
        authorList.setSelectedIndex(0);

        bookSelectedId = null;
        saveButton.setText("Save");
        deleteButton.setVisible(false);
    }

    private void loadBookDataToForm(int row) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) listBook.getModel();

        // Obtener los datos del libro seleccionado
        bookSelectedId = (Integer) model.getValueAt(row, 0);
        String title = (String) model.getValueAt(row, 1);
        String genre = (String) model.getValueAt(row, 2);
        Integer totalPages = (Integer) model.getValueAt(row, 3);
        String editorial = (String) model.getValueAt(row, 4);
        String authors = (String) model.getValueAt(row, 5);
        Integer publicYear = (Integer) model.getValueAt(row, 6);

        // Cargar datos en los campos
        bookName.setText(title);
        genreList.setSelectedItem(genre);
        totalPagesField.setText(String.valueOf(totalPages));
        editorialField.setText(editorial);
        publicYearField.setText(String.valueOf(publicYear));
        // Cargar el autor correspondiente en el JComboBox
        authorList.setSelectedItem(getAuthorFromList(authors));

        saveButton.setText("Update");
    }


    private String getAuthorFromList(String authors) throws SQLException {
        String[] authorData = authors.split("-");
        String authorId = authorData[1].trim();
        Author author = authorService.findById(Integer.parseInt(authorId));
        return author.getId() + " - " + author.getName() + " " + author.getLastname();
    }

    public static void main(String[] args) throws SQLException, IOException {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}
