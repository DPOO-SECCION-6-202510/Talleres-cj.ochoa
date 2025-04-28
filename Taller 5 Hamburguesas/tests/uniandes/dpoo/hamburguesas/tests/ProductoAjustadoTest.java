package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest {
	
	private ProductoAjustado productoAjustado;
	private ProductoMenu productoBase;
	private Ingrediente añadido1;
	private Ingrediente añadido2;
	private Ingrediente añadido3;
	private Ingrediente eliminado1;
	private Ingrediente eliminado2;
	private Ingrediente eliminado3;
	
	
    @BeforeEach
    void setUp( ) throws Exception
    {
    	añadido1 = new Ingrediente( "uñas de pie", 5000 );
    	añadido2 = new Ingrediente( "huevas de toro", 7000 );
    	añadido3 = new Ingrediente( "miel de elfante", 2000 );
    	eliminado1 = new Ingrediente( "salsa de tomate", 2000 );
    	eliminado2 = new Ingrediente( "mayonesa", 2000 );
    	eliminado3 = new Ingrediente( "arroz blanco", 7000 );

    	productoBase = new ProductoMenu( "Iguana Frita", 50000 );
    	productoAjustado = new ProductoAjustado( productoBase );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }
    
    @Test
    void testAddIngrediente( )
    {
    	productoAjustado.addIngrediente(añadido1);
    	productoAjustado.addIngrediente(añadido2);
    	productoAjustado.addIngrediente(añadido3);
    	
    	ArrayList<Ingrediente> addeds = new ArrayList<Ingrediente>();
    	addeds.add(añadido1);
    	addeds.add(añadido2);
    	addeds.add(añadido3);
    	
    	
        assertEquals( addeds, productoAjustado.getAgregados(), "Los productos agregados no son los esperados" );
    }
    
    @Test
    void testRemoveIngrediente( )
    {
    	productoAjustado.removeIngrediente(eliminado1);
    	productoAjustado.addIngrediente(eliminado2);
    	productoAjustado.addIngrediente(eliminado3);
    	
    	ArrayList<Ingrediente> removed = new ArrayList<Ingrediente>();
    	removed.remove(eliminado1);
    	removed.remove(eliminado2);
    	removed.remove(eliminado3);
    	
    	
        assertEquals( removed, productoAjustado.getEliminados(), "Los productos eliminados no son los esperados" );
    }
    
    @Test
    void testGetNombre( )
    {
        assertEquals( "Iguana Frita", productoAjustado.getNombre( ), "El nombre del producto no es el esperado." );
    }
    
    @Test
    void testGetPrecio( )
    {
    	productoAjustado.addIngrediente(añadido1);
    	productoAjustado.addIngrediente(añadido2);
    	productoAjustado.addIngrediente(añadido3);
    	productoAjustado.removeIngrediente(eliminado1);
    	productoAjustado.removeIngrediente(eliminado2);
    	productoAjustado.removeIngrediente(eliminado3);
        assertEquals( 64000, productoAjustado.getPrecio( ), "El precio del producto no es el esperado." );
        
    	productoAjustado = new ProductoAjustado( productoBase );
    	productoAjustado.removeIngrediente(eliminado1);
    	productoAjustado.removeIngrediente(eliminado2);
        assertEquals( 50000, productoAjustado.getPrecio( ), "El precio del producto no es el esperado." );
        
    	productoAjustado = new ProductoAjustado( productoBase );
    	productoAjustado.addIngrediente(añadido2);	
    	assertEquals( 57000, productoAjustado.getPrecio( ), "El precio del producto no es el esperado." );
    }
	
    @Test
    void testGenerarTextoFactura( )
    {
    	productoAjustado.addIngrediente(añadido1);
    	productoAjustado.addIngrediente(añadido2);
    	productoAjustado.addIngrediente(añadido3);
    	productoAjustado.removeIngrediente(eliminado1);
    	productoAjustado.removeIngrediente(eliminado2);
    	productoAjustado.removeIngrediente(eliminado3);
    	
    	
        StringBuffer sb = new StringBuffer( );
        sb.append( productoBase );
        for( Ingrediente ing : productoAjustado.getAgregados() )
        {
            sb.append( "    +" + ing.getNombre( ) );
            sb.append( "                " + ing.getCostoAdicional( ) );
        }
        for( Ingrediente ing : productoAjustado.getEliminados() )
        {
            sb.append( "    -" + ing.getNombre( ) );
        }

        sb.append( "            " + productoAjustado.getPrecio( ) + "\n" );
    	
        assertEquals( sb.toString( ), productoAjustado.generarTextoFactura( ), "El texto generado no es el esperado." );
    }
    
}
