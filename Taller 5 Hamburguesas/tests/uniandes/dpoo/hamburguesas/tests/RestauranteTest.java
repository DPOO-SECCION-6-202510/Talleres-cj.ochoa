package uniandes.dpoo.hamburguesas.tests;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.rules.TemporaryFolder;
import uniandes.dpoo.hamburguesas.mundo.*;
import uniandes.dpoo.hamburguesas.excepciones.*;

public class RestauranteTest {
    private Restaurante restaurante;
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    private File archivoIngredientes;
    private File archivoMenu;
    private File archivoCombos;
    private File archivoIngredientesRepetidos;
    private File archivoMenuRepetido;
    private File archivoCombosFaltantes;
    private File archivoCombosRepetidos;

    @Before
    public void setUp() throws Exception {
        restaurante = new Restaurante();
        
        // Create normal test files
        archivoIngredientes = createTempFile("ingredientes.txt", 
            "Tomate;1000\nLechuga;800\nQueso;1500");
        
        archivoMenu = createTempFile("menu.txt", 
            "Hamburguesa Clásica;15000\nHamburguesa Especial;20000\nPapas Pequeñas;5000");
        
        archivoCombos = createTempFile("combos.txt", 
            "Combo Clásico;10%;Hamburguesa Clásica;Papas Pequeñas\n" +
            "Combo Especial;15%;Hamburguesa Especial;Papas Pequeñas");
        
        // Create exception test files
        archivoIngredientesRepetidos = createTempFile("ingredientes_repetidos.txt", 
            "Tomate;1000\nLechuga;800\nTomate;1500");
        
        archivoMenuRepetido = createTempFile("menu_repetido.txt", 
            "Hamburguesa Clásica;15000\nHamburguesa Especial;20000\nHamburguesa Clásica;18000");
        
        archivoCombosFaltantes = createTempFile("combos_faltantes.txt", 
            "Combo Imposible;10%;Hamburguesa Imposible;Papas Pequeñas");
        
        archivoCombosRepetidos = createTempFile("combos_repetidos.txt", 
            "Combo Clásico;10%;Hamburguesa Clásica;Papas Pequeñas\n" +
            "Combo Clásico;15%;Hamburguesa Clásica;Papas Grandes");
    }
    
    @AfterEach
    void tearDown( ) throws Exception
    {
    }
    
    private File createTempFile(String filename, String content) throws IOException {
        File file = tempFolder.newFile(filename);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
        return file;
    }

    @Test
    public void testIniciarPedido() throws YaHayUnPedidoEnCursoException {
        restaurante.iniciarPedido("Jefferson", "Su casa");
        assertNotNull(restaurante.getPedidoEnCurso());
        assertEquals("Jefferson", restaurante.getPedidoEnCurso().getNombreCliente());
    }

    @Test(expected = YaHayUnPedidoEnCursoException.class)
    public void testIniciarPedidoConPedidoExistente() throws YaHayUnPedidoEnCursoException {
        restaurante.iniciarPedido("Jefferson", "Su casa");
        restaurante.iniciarPedido("Jaiderson", "La casa de Jeffeson");
    }

    @Test
    public void testCerrarYGuardarPedido() throws YaHayUnPedidoEnCursoException, NoHayPedidoEnCursoException, IOException {
        restaurante.iniciarPedido("Jefferson", "Su casa");
        restaurante.cerrarYGuardarPedido();
        assertNull(restaurante.getPedidoEnCurso());
        assertEquals(1, restaurante.getPedidos().size());
    }

    @Test(expected = NoHayPedidoEnCursoException.class)
    public void testCerrarYGuardarPedidoSinPedido() throws NoHayPedidoEnCursoException, IOException {
        restaurante.cerrarYGuardarPedido();
    }

    @Test
    public void testCargarInformacionRestaurante() throws HamburguesaException, NumberFormatException, IOException {
        restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
        
        assertFalse(restaurante.getIngredientes().isEmpty());
        assertFalse(restaurante.getMenuBase().isEmpty());
        assertFalse(restaurante.getMenuCombos().isEmpty());
    }

    @Test(expected = IngredienteRepetidoException.class)
    public void testCargarIngredientesRepetidos() throws HamburguesaException, NumberFormatException, IOException {
        restaurante.cargarInformacionRestaurante(archivoIngredientesRepetidos, archivoMenu, archivoCombos);
    }

    @Test(expected = ProductoRepetidoException.class)
    public void testCargarMenuRepetido() throws HamburguesaException, NumberFormatException, IOException {
        restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenuRepetido, archivoCombos);
    }

    @Test(expected = ProductoFaltanteException.class)
    public void testCargarCombosConProductoFaltante() throws HamburguesaException, NumberFormatException, IOException {
        restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombosFaltantes);
    }

    @Test(expected = ProductoRepetidoException.class)
    public void testCargarCombosRepetidos() throws HamburguesaException, NumberFormatException, IOException {
        restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombosRepetidos);
    }

    @Test
    public void testGetPedidos() throws YaHayUnPedidoEnCursoException, NoHayPedidoEnCursoException, IOException {
        assertEquals(0, restaurante.getPedidos().size());
        
        restaurante.iniciarPedido("Jefferson", "Su casa");
        restaurante.cerrarYGuardarPedido();
        
        assertEquals(1, restaurante.getPedidos().size());
    }

    @Test
    public void testGetMenuBase() throws HamburguesaException, NumberFormatException, IOException {
        restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
        ArrayList<ProductoMenu> menu = restaurante.getMenuBase();
        assertNotNull(menu);
        assertFalse(menu.isEmpty());
    }

    @Test
    public void testGetMenuCombos() throws HamburguesaException, NumberFormatException, IOException {
        restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
        ArrayList<Combo> combos = restaurante.getMenuCombos();
        assertNotNull(combos);
        assertFalse(combos.isEmpty());
    }

    @Test
    public void testGetIngredientes() throws HamburguesaException, NumberFormatException, IOException {
        restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
        ArrayList<Ingrediente> ingredientes = restaurante.getIngredientes();
        assertNotNull(ingredientes);
        assertFalse(ingredientes.isEmpty());
    }
}