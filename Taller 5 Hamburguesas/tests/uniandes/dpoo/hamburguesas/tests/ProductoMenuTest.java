package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest {

	private ProductoMenu productoMenu1;
	
    @BeforeEach
    void setUp( ) throws Exception
    {
    	productoMenu1 = new ProductoMenu( "Iguana Frita", 50000 );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }
	
    @Test
    void testGetNombre( )
    {
        assertEquals( "Iguana Frita", productoMenu1.getNombre( ), "El nombre del producto no es el esperado." );
    }
    
    @Test
    void testGetPrecio( )
    {
        assertEquals( 50000, productoMenu1.getPrecio( ), "El costo del producto no es el esperado." );
    }
    
    @Test
    void testGenerarTextoFactura( )
    {
        StringBuffer sb = new StringBuffer( );
        sb.append( "Iguana Frita" + "\n" );
        sb.append( "            " + 50000 + "\n" );
        assertEquals( sb.toString( ), productoMenu1.generarTextoFactura( ), "El texto generado no es el esperado." );
        
    }
    
}
