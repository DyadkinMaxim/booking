package com.example.bookings;

import com.example.bookings.exception.NotFoundException;
import com.example.bookings.exception.UnprocessableException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.bookings.TestData.BOOKINGS_BY_DATE;
import static com.example.bookings.TestData.FOUND_BOOKING;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindBookingsByDate() throws Exception {
        this.mockMvc.perform(get("/listBookings/2022-12-05"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(BOOKINGS_BY_DATE));
    }

    @Test
    public void testFindById() throws Exception {
        this.mockMvc.perform(get("/getBooking/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(FOUND_BOOKING));
    }

    @Test
    public void testFindByInvalidId() throws Exception {
        this.mockMvc.perform(get("/getBooking/-1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    public void testSave() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/createBooking/1/2022-12-05/99"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveInvalidDate() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/createBooking/1/2022-1205/99"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnprocessableException));
    }

    @Test
    public void testSaveInvalidSeat() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/createBooking/1/2022-12-05/111"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnprocessableException));
    }

    @Test
    public void testSaveDuplicate() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/createBooking/1/2022-12-05/100"))
                .andExpect(status().isUnprocessableEntity()) //HTTP 422 for service logic simplicity - 409 Conflict is preferable
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnprocessableException));
    }

    @Test
    public void testDelete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/cancelBooking/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}