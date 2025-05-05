package org.com.dianping.service;

import org.com.dianping.entity.Merchant;
import org.com.dianping.repository.MerchantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MerchantServiceTest {

    @Mock
    private MerchantRepository merchantRepository;

    private MerchantService merchantService;

    @BeforeEach
    void setUp() {
        merchantService = new MerchantService(merchantRepository);
    }

    @Test
    void getMerchants_WithValidKeyword_ShouldReturnFilteredResults() {
        // Arrange
        Merchant merchant1 = createMerchant(1L, "Restaurant A", 4.5f, 100f);
        Merchant merchant2 = createMerchant(2L, "Restaurant B", 4.0f, 80f);
        when(merchantRepository.searchMerchantsWithPinyin(anyString(), anyFloat(), anyFloat(), anyFloat()))
                .thenReturn(Arrays.asList(merchant1, merchant2));

        // Act
        List<Merchant> results = merchantService.getMerchants("Restaurant", 4.0f, "80-120", null, "rating");

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        assertTrue(results.get(0).getRating() >= results.get(1).getRating());
    }

    @Test
    void getMerchants_WithChineseKeyword_ShouldSearchWithPinyin() {
        // Arrange
        Merchant merchant = createMerchant(1L, "餐厅", 4.5f, 100f);
        when(merchantRepository.searchMerchantsWithPinyin(anyString(), isNull(), isNull(), isNull()))
                .thenReturn(List.of(merchant));

        // Act
        List<Merchant> results = merchantService.getMerchants("餐厅", null, null, null, null);

        // Assert
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals("餐厅", results.get(0).getMerchantName());
    }

    @Test
    void getMerchantById_WithExistingId_ShouldReturnMerchant() {
        // Arrange
        Merchant merchant = createMerchant(1L, "Test Merchant", 4.5f, 100f);
        when(merchantRepository.findById(1L)).thenReturn(Optional.of(merchant));

        // Act
        Optional<Merchant> result = merchantService.getMerchantById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Merchant", result.get().getMerchantName());
    }

    @Test
    void getMerchantById_WithNonexistentId_ShouldReturnEmpty() {
        // Arrange
        when(merchantRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Merchant> result = merchantService.getMerchantById(999L);

        // Assert
        assertTrue(result.isEmpty());
    }

    private Merchant createMerchant(Long id, String name, Float rating, Float avgPrice) {
        Merchant merchant = new Merchant();
        merchant.setId(id);
        merchant.setMerchantName(name);
        merchant.setRating(rating);
        merchant.setAvgPrice(avgPrice);
        return merchant;
    }
}
