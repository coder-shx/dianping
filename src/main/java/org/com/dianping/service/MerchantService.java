package org.com.dianping.service;

import jakarta.transaction.Transactional;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.com.dianping.entity.Merchant;
import org.com.dianping.repository.MerchantRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Transactional
    public List<Merchant> getMerchants(String keyword, Float minRating,
                                       String priceRange, Float avgPrice,
                                       String sortType) {
        Float minPrice = null;
        Float maxPrice = null;

        // 处理价格区间
        if (priceRange != null && !priceRange.isEmpty()) {
            String[] parts = priceRange.split("-");
            if (parts.length == 2) {
                minPrice = Float.parseFloat(parts[0]);
                maxPrice = Float.parseFloat(parts[1]);
            } else if (priceRange.endsWith("-")) {
                minPrice = Float.parseFloat(priceRange.substring(0, priceRange.length() - 1));
            } else if (priceRange.startsWith("-")) {
                maxPrice = Float.parseFloat(priceRange.substring(1));
            } else {
                minPrice = Float.parseFloat(priceRange);
            }
        }
        if (avgPrice != null) {
            maxPrice = avgPrice;
        }

        // 如果 keyword 是汉字，转换为拼音
        String keywordPinyin = null;
        if (keyword != null && keyword.matches("[\\u4e00-\\u9fa5]+")) {
            keywordPinyin = convertToPinyin(keyword);
        }

        List<Merchant> results = merchantRepository.searchMerchantsWithPinyin(
                keyword, minRating, minPrice, maxPrice);

        // 如果 keywordPinyin 不为空，追加拼音匹配结果
        if (keywordPinyin != null) {
            List<Merchant> pinyinResults = merchantRepository.searchMerchantsWithPinyin(
                    keywordPinyin, minRating, minPrice, maxPrice);
            results.addAll(pinyinResults);
        }

        // 去重
        results = results.stream().distinct().toList();

        // 应用排序
        if (sortType != null) {
            switch (sortType) {
                case "rating":
                    results.sort((a, b) -> Float.compare(b.getRating(), a.getRating()));
                    break;
                case "price_asc":
                    results.sort((a, b) -> Float.compare(a.getAvgPrice(), b.getAvgPrice()));
                    break;
                case "price_desc":
                    results.sort((a, b) -> Float.compare(b.getAvgPrice(), a.getAvgPrice()));
                    break;
            }
        }
        return results;
    }

    private String convertToPinyin(String chinese) {
        StringBuilder pinyin = new StringBuilder();
        for (char c : chinese.toCharArray()) {
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
            if (pinyinArray != null) {
                // 去掉音调标记
                pinyin.append(pinyinArray[0].replaceAll("\\d", ""));
            } else {
                pinyin.append(c);
            }
        }
        return pinyin.toString();
    }

    public List<Merchant> searchMerchantsByKeyword(String keyword) {
        return merchantRepository.findByKeyword(keyword);
    }

    public Optional<Merchant> getMerchantById(Long id) {
        return merchantRepository.findById(id);
    }

    public List<Merchant> searchMerchantsWithPinyin(String keyword) {
        return merchantRepository.searchMerchantsWithPinyin(keyword, null, null, null);
    }
}