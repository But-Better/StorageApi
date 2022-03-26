package com.butbetter.storage.fileUpload;

import java.nio.file.Paths;
import java.util.stream.Stream;

import com.butbetter.storage.csvImport.exception.StorageFileNotFoundException;
import com.butbetter.storage.csvImport.exception.StorageFileNotProcessableException;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class FileUploadControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private com.butbetter.storage.csvImport.service.fileStorage.IFileStorageService IFileStorageService;

    @Test
    public void shouldListAllFiles() throws Exception {
        given(this.IFileStorageService.loadAll())
                .willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));

        this.mvc.perform(get("/csv/v1/")).andExpect(status().isOk());
    }

    @Test
    public void shouldSaveUploadedFile() throws Exception, StorageFileNotFoundException, StorageFileNotProcessableException {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());
        this.mvc.perform(multipart("/csv/v1/").file(multipartFile))
                .andExpect(status().is2xxSuccessful());

        then(this.IFileStorageService).should().store(multipartFile);
    }

    @Test
    public void should404WhenMissingFile() throws Exception, StorageFileNotFoundException {
        given(this.IFileStorageService.loadAsResource("test.txt")).willThrow(StorageFileNotFoundException.class);

        this.mvc.perform(get("/csv/v1/test.txt")).andExpect(status().is4xxClientError());
    }

}