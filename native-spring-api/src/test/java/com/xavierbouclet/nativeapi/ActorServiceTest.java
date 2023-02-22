package com.xavierbouclet.nativeapi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActorServiceTest {
     private ActorService actorService;


    @Mock
    private ActorRepository actorRepository;
    @Mock
    private MovieRepository movieRepository;

    @Before
    public void before() {
        actorService = new ActorService(actorRepository, movieRepository);
    }

    @Test
    public void list() {
        var actor=mock(Actor.class);
        List<Actor> actors = List.of(actor);
        when(actorRepository.findAll()).thenReturn(actors);
        actorService = new ActorService(actorRepository, movieRepository);
        assertThat(actorService.list()).isEqualTo(actors);
    }
}