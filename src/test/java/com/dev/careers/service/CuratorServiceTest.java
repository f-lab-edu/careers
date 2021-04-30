package com.dev.careers.service;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.dev.careers.domain.Curator;
import com.dev.careers.repository.CuratorRepository;
import com.dev.careers.util.encryption.Sha256Encrypt;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CuratorServiceTest {

    CuratorService curatorService;

    @Mock
    CuratorRepository curatorRepository;

    @Mock
    Sha256Encrypt sha256Encrypt;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        //curatorService = new CuratorService(curatorRepository, sha256Encrypt);
    }

    @Test
    public void addCurator(){
        Curator curator = new Curator(1L,"admin",
            "Abc12345@","admin@curators.com");

        verify(curatorRepository).save(any());

        assertThat(curator.getName(), is("aa"));

    }
}
