import com.techtask.controller.WalletController;
import com.techtask.dto.WalletUpdateRequest;
import com.techtask.exception.InsufficientFundsException;
import com.techtask.exception.InvalidJsonException;
import com.techtask.exception.WalletNotFoundException;
import com.techtask.model.OperationType;
import com.techtask.model.Wallet;
import com.techtask.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class WalletControllerTest {

    @Test
    void testUpdateWalletBalance_Success() {
        WalletService walletService = mock(WalletService.class);
        WalletController controller = new WalletController(walletService);
        WalletUpdateRequest request = new WalletUpdateRequest();
        request.setWalletId(UUID.randomUUID());
        request.setOperationType(OperationType.DEPOSIT);
        request.setAmount(100.0);

        ResponseEntity<String> response = controller.updateWalletBalance(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Wallet balance updated successfully", response.getBody());
    }

    @Test
    void testUpdateWalletBalance_InsufficientFundsException() {
        WalletService walletService = mock(WalletService.class);
        WalletController controller = new WalletController(walletService);
        WalletUpdateRequest request = new WalletUpdateRequest();
        request.setWalletId(UUID.randomUUID());
        request.setOperationType(OperationType.WITHDRAW);
        request.setAmount(100.0);
        doThrow(new InsufficientFundsException("Insufficient funds"))
                .when(walletService).updateWalletBalance(any(UUID.class), any(OperationType.class), anyDouble());

        ResponseEntity<String> response = controller.updateWalletBalance(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Insufficient funds", response.getBody());
    }

    @Test
    void testGetWallet_Success() {
        WalletService walletService = mock(WalletService.class);
        WalletController controller = new WalletController(walletService);
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet();
        when(walletService.getWallet(walletId)).thenReturn(wallet);

        ResponseEntity<?> response = controller.getWallet(walletId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(wallet, response.getBody());
    }

    @Test
    void testGetWallet_WalletNotFoundException() {
        WalletService walletService = mock(WalletService.class);
        WalletController controller = new WalletController(walletService);
        UUID walletId = UUID.randomUUID();
        when(walletService.getWallet(walletId)).thenThrow(new WalletNotFoundException("Wallet not found"));

        ResponseEntity<?> response = controller.getWallet(walletId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Wallet not found", response.getBody());
    }

    @Test
    void testUpdateWalletBalance_InvalidJsonException() {
        WalletService walletService = mock(WalletService.class);
        WalletController controller = new WalletController(walletService);
        WalletUpdateRequest request = new WalletUpdateRequest();
        request.setWalletId(UUID.randomUUID());
        request.setOperationType(OperationType.DEPOSIT);
        request.setAmount(100.0);
        doThrow(new InvalidJsonException("Invalid JSON"))
                .when(walletService).updateWalletBalance(any(UUID.class), any(OperationType.class), anyDouble());

        ResponseEntity<String> response = controller.updateWalletBalance(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid JSON", response.getBody());
    }
}
