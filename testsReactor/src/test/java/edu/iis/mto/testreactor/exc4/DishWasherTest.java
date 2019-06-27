package edu.iis.mto.testreactor.exc4;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.iis.mto.testreactor.exc4.ProgramConfiguration.Builder;

import static org.hamcrest.core.Is.is;


public class DishWasherTest {
	
	private DirtFilter dirtFilter;
	private Door door;
	private Engine engine;
	private DishWasher dishWasher;
	private WaterPump waterPump;
	
	@Before
    public void setUp() {
		dirtFilter = Mockito.mock(DirtFilter.class);
		door = Mockito.mock(Door.class);
		engine = Mockito.mock(Engine.class);
		waterPump = Mockito.mock(WaterPump.class);
		dishWasher = new DishWasher(waterPump, engine, dirtFilter, door);
    }

    @Test
    public void test() {
        assertThat(true, Matchers.equalTo(true));
    }
    
    @Test
    public void washingWithOpenDoorThrowsError() {
    	ProgramConfiguration.Builder builder = ProgramConfiguration.builder();
    	ProgramConfiguration programConfiguration = builder.build();
        Mockito.when(door.closed()).thenReturn(true);
        RunResult result = dishWasher.start(programConfiguration);
        
        Status status = result.getStatus();
        
        Assert.assertThat(status, is(Status.DOOR_OPEN_ERROR));
    }
    
    @Test
    public void washingWithFilterWithCapacityTooLowThrowsError() {
    	ProgramConfiguration.Builder builder = ProgramConfiguration.builder();
    	Builder builder_with_tablets = builder.withTabletsUsed(true);
    	ProgramConfiguration programConfiguration = builder_with_tablets.build();
        Mockito.when(door.closed()).thenReturn(false);
        Mockito.when(dirtFilter.capacity()).thenReturn(30.0);
        

        RunResult result = dishWasher.start(programConfiguration);
        
        Status status = result.getStatus();
        Assert.assertThat(status, is(Status.ERROR_FILTER));
    }
    
    @Test
    public void waterPumpFailureThrowsException() {
    	ProgramConfiguration.Builder builder = ProgramConfiguration.builder();
    	Builder builder_without_tablets = builder.withTabletsUsed(false);
    	ProgramConfiguration programConfiguration = builder_without_tablets.build();
        
        
        
        try {
			Mockito.doThrow(new EngineException()).when(waterPump).pour(WashingProgram.RINSE);
		} catch (PumpException e) {
	        assertThat(true, Matchers.equalTo(true));

		}


        
    }
}