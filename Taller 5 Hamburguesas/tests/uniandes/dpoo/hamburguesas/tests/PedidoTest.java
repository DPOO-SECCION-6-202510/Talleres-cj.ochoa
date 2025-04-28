package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Producto;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest {
	
	private Pedido pedidojiji;
	
    @BeforeEach
    void setUp( ) throws Exception
    {
    	pedidojiji = new Pedido( "Edgardo Aiderson", "Cra. 999 #44-jaja" );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetIdPedido( )
    {
        assertEquals( Pedido.numeroPedidos-1, pedidojiji.getIdPedido( ), "El id del pedido no es el esperado." );
    	Pedido pedidojaja = new Pedido( "", "" );
        assertEquals( Pedido.numeroPedidos-1, pedidojaja.getIdPedido( ), "El id del pedido no es el esperado." );
    }
    
    @Test
    void testGetNombreCliente( )
    {
        assertEquals( "Edgardo Aiderson", pedidojiji.getNombreCliente( ), "El nombre del cliente no es el esperado." );
    }
    
    @Test
    void testGetPrecioTotalPedido( )
    {
    	ProductoMenu productoBase = new ProductoMenu( "Iguana Frita", 50000 );
    	Ingrediente añadido1 = new Ingrediente( "uñas de pie", 5000 );
    	Ingrediente añadido2 = new Ingrediente( "huevas de toro", 7000 );
    	Ingrediente añadido3 = new Ingrediente( "miel de elfante", 2000 );
    	ProductoAjustado prod1 = new ProductoAjustado( productoBase );
    	prod1.addIngrediente(añadido1);
    	prod1.addIngrediente(añadido2);
    	prod1.addIngrediente(añadido3);
    	pedidojiji.agregarProducto(prod1);
    	
    	ArrayList<ProductoMenu> items = new ArrayList<>();
    	ProductoMenu hamburguesa = new ProductoMenu("hamburguesa deshuesada", 50000);
    	ProductoMenu bebida = new ProductoMenu("gaseosa deshuesada", 6900);
    	ProductoMenu papitas = new ProductoMenu("papitas deshuesadas", 7000);
    	items.add(hamburguesa);
    	items.add(bebida);
    	items.add(papitas);
    	Combo combojiji = new Combo( "combo deshuesado", 0.1, items );
    	pedidojiji.agregarProducto(combojiji);
    	
    	int precio = (int) ((prod1.getPrecio() + combojiji.getPrecio()) * 1.19);
    	
        assertEquals( precio, pedidojiji.getPrecioTotalPedido( ), "El precio total del pedido no es el esperado." );
    }
    
    @Test
    void testGenerarTextoFactura( )
    {
        StringBuffer sb = new StringBuffer( );

        sb.append( "Cliente: " + "Edgardo Aiderson" + "\n" );
        sb.append( "Dirección: " + "Cra. 999 #44-jaja" + "\n" );
        sb.append( "----------------\n" );

        ArrayList<Producto> productos = new ArrayList<>();
    	ProductoMenu productoBase = new ProductoMenu( "Iguana Frita", 50000 );
    	Ingrediente añadido1 = new Ingrediente( "uñas de pie", 5000 );
    	Ingrediente añadido2 = new Ingrediente( "huevas de toro", 7000 );
    	Ingrediente añadido3 = new Ingrediente( "miel de elfante", 2000 );
    	ProductoAjustado prod1 = new ProductoAjustado( productoBase );
    	prod1.addIngrediente(añadido1);
    	prod1.addIngrediente(añadido2);
    	prod1.addIngrediente(añadido3);
    	productos.add(prod1);
    	pedidojiji.agregarProducto(prod1);
    	
    	ArrayList<ProductoMenu> items = new ArrayList<>();
    	ProductoMenu hamburguesa = new ProductoMenu("hamburguesa deshuesada", 50000);
    	ProductoMenu bebida = new ProductoMenu("gaseosa deshuesada", 6900);
    	ProductoMenu papitas = new ProductoMenu("papitas deshuesadas", 7000);
    	items.add(hamburguesa);
    	items.add(bebida);
    	items.add(papitas);
    	Combo combojiji = new Combo( "combo deshuesado", 0.1, items );
    	productos.add(combojiji);
    	pedidojiji.agregarProducto(combojiji);
        
        for( Producto item : productos )
        {
            sb.append( item.generarTextoFactura( ) );
        }

        sb.append( "----------------\n" );
        sb.append( "Precio Neto:  " + (prod1.getPrecio() + combojiji.getPrecio()) + "\n" );
        sb.append( "IVA:          " + (int) ((prod1.getPrecio() + combojiji.getPrecio()) * 0.19) + "\n" );
        sb.append( "Precio Total: " + (int) ((prod1.getPrecio() + combojiji.getPrecio()) * 1.19) + "\n" );
        
        assertEquals( sb.toString( ), pedidojiji.generarTextoFactura( ), "El texto generado no es el esperado." );
    }
    
    @Test
    void testGuardarFactura( ) throws IOException {
    	File tempFile = File.createTempFile("factura", ".txt");
        tempFile.deleteOnExit();
        
    	pedidojiji.guardarFactura(tempFile);
    	
        assertTrue(tempFile.exists(), "El archivo no existe.");
        assertTrue(tempFile.length() > 0, "El archivo está vacío.");
        
        String content = Files.readString(tempFile.toPath());
        assertEquals(pedidojiji.generarTextoFactura(), content, "El contenido no es igual.");
    }

}
