package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest {

	private Combo combojiji;
	
    @BeforeEach
    void setUp( ) throws Exception
    {
    	ArrayList<ProductoMenu> items = new ArrayList<>();
    	ProductoMenu hamburguesa = new ProductoMenu("hamburguesa deshuesada", 50000);
    	ProductoMenu bebida = new ProductoMenu("gaseosa deshuesada", 6900);
    	ProductoMenu papitas = new ProductoMenu("papitas deshuesadas", 7000);
    	items.add(hamburguesa);
    	items.add(bebida);
    	items.add(papitas);
    	
    	combojiji = new Combo( "combo deshuesado", 0.1, items );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }
	
    @Test
    void testGetNombre( )
    {
        assertEquals( "combo deshuesado", combojiji.getNombre( ), "El nombre del combo no es el esperado." );
    }
    
    @Test
    void testGetPrecio( )
    {
    	int precio = (int) ((50000 + 6900 + 7000)*0.9);
        assertEquals( precio, combojiji.getPrecio( ), "El precio del combo no es el esperado." );
        
    	ArrayList<ProductoMenu> items = new ArrayList<>();
    	ProductoMenu hamburguesa = new ProductoMenu("hamburguesa deshuesada", 50000);
    	ProductoMenu bebida = new ProductoMenu("gaseosa deshuesada", 6900);
    	ProductoMenu papitas = new ProductoMenu("papitas deshuesadas", 7000);
    	items.add(hamburguesa);
    	items.add(bebida);
    	items.add(papitas);
    	combojiji = new Combo( "combo deshuesado", 1, items );
    	assertEquals( 0, combojiji.getPrecio( ), "El precio del combo no es el esperado." );
    	
    	combojiji = new Combo( "combo deshuesado", 0, items );
    	assertEquals( (50000 + 6900 + 7000), combojiji.getPrecio( ), "El precio del combo no es el esperado." );
    }
    
    @Test
    void testGetDescuento( )
    {
        assertEquals( 0.1, combojiji.getDescuento( ), "El descuento del combo no es el esperado." );
    }
    
    @Test
    void testGenerarFactura( )
    {
        StringBuffer sb = new StringBuffer( );
        sb.append( "Combo " + combojiji.getNombre( ) + "\n" );
        sb.append( " Descuento: " + combojiji.getDescuento( ) + "\n" );
        sb.append( "            " + combojiji.getPrecio( ) + "\n" );
    	
        assertEquals( sb.toString( ), combojiji.generarTextoFactura( ), "El texto generado no es el esperado." );
    }
    
}
