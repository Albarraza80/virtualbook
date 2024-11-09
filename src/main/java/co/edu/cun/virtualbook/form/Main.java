package co.edu.cun.virtualbook.form;

import co.edu.cun.virtualbook.domain.Author;
import co.edu.cun.virtualbook.domain.Book;
import co.edu.cun.virtualbook.domain.Category;
import co.edu.cun.virtualbook.service.AuthorService;
import co.edu.cun.virtualbook.service.BookService;
import co.edu.cun.virtualbook.service.CategoryService;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private JLabel title;
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
        DefaultTableModel model = new DefaultTableModel() {
            // Sobrescribir el método para evitar la edición de las celdas
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Ninguna celda será editable
            }
        };

        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Genre");
        model.addColumn("Number Pages");
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
                    deleteButton.setEnabled(true);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        mainPanel,
                        "Are you sure you want to delete this record?",
                        "Confirm deletion",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        bookService.delete(bookSelectedId);
                        clearForm();
                        refreshTable();
                        JOptionPane.showMessageDialog(null, "Record deleted successfully!");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error deleting record: " + ex.getMessage());
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    // Verificar que los campos estén completos antes de guardar
                    if (!areFieldsValid()) {
                        return; // Detener si algún campo está vacío
                    }
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

        // Validar que solo se ingresen números en totalPagesField
        totalPagesField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();  // Ignorar la tecla si no es un dígito
                    JOptionPane.showMessageDialog(mainPanel, "Please enter only numeric values for Total Pages.");
                }
            }
        });

        // Validar que solo se ingresen números en publicYearField
        publicYearField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();  // Ignorar la tecla si no es un dígito
                    JOptionPane.showMessageDialog(mainPanel, "Please enter only numeric values for Publication Year.");
                }
            }
        });

        deleteButton.setEnabled(false);
    }

    // Método para verificar que todos los campos estén completos
    private boolean areFieldsValid() {
        if (bookName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Please enter the book name.");
            bookName.requestFocus();
            return false;
        }
        if (genreList.getSelectedItem() == null || genreList.getSelectedItem().toString().equals("Select a genre")) {
            JOptionPane.showMessageDialog(mainPanel, "Please select a genre.");
            genreList.requestFocus();
            return false;
        }
        if (totalPagesField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Please enter the total pages.");
            totalPagesField.requestFocus();
            return false;
        }
        if (editorialField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Please enter the editorial.");
            editorialField.requestFocus();
            return false;
        }
        if (authorList.getSelectedItem() == null || authorList.getSelectedItem().toString().equals("Select an author")) {
            JOptionPane.showMessageDialog(mainPanel, "Please select an author.");
            authorList.requestFocus();
            return false;
        }
        if (publicYearField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Please enter the publication year.");
            publicYearField.requestFocus();
            return false;
        }
        return true;
    }

    private void loadAuthors() throws SQLException {
        // Obtener todos los autores de AuthorService
        List<Author> authors = authorService.findAll();

        // Agregar cada autor al JComboBox
        authorList.addItem("Select an author");
        for (Author author : authors) {
            authorList.addItem(author.getId() + " - " +author.getName() + " " + author.getLastname());
        }
    }

    private void loadGenres() throws SQLException {
        // Obtener todas las categorías de CategoryService
        List<Category> categories = categoryService.findAll();

        // Agregar cada categoría al JComboBox
        genreList.addItem("Select a genre");
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

            String message = "Book " + book.getTitle() + " successfully created!";
        JOptionPane.showMessageDialog(mainPanel, message);
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

        String message = "Book " + book.getTitle() + " successfully updated!";
        JOptionPane.showMessageDialog(mainPanel, message);
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
        deleteButton.setEnabled(false);
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
        JFrame frame = new JFrame(".:: Virtual Book ::.");
        frame.setContentPane(new Main().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setSize(900, 600);
        frame.setVisible(true);
    }

}
