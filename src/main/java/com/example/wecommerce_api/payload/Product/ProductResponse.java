package com.example.wecommerce_api.payload.Product;

import com.example.wecommerce_api.entity.PhotoEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for products shown to the buyer side of the marketplace.
 * <p>
 * Two constructors exist:
 *   - 8-arg legacy: kept for existing call sites that don't have seller info
 *     handy (sellerId / sellerName default to null in that path).
 *   - 10-arg full: preferred — pass seller info so the frontend can render
 *     "by {shopName}" and link to /shop/{sellerId}.
 */
@Data
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String title;
    private Double price;
    private String status;
    private Boolean isSaved;
    private LocalDateTime createdDate;
    private Double totalAmount;
    private List<PhotoEntity> photo;

    /** Seller info — frontend uses these to render "by {sellerName}" and link to /shop/{sellerId}. */
    private Long sellerId;
    private String sellerName;

    /** Legacy constructor — no seller info. Keeps existing callsites compiling. */
    public ProductResponse(Long id, String title, Double price, String status,
                           Boolean isSaved, LocalDateTime createdDate,
                           Double totalAmount, List<PhotoEntity> photo) {
        this(id, title, price, status, isSaved, createdDate, totalAmount, photo, null, null);
    }

    /** Full constructor — preferred for new callsites. */
    public ProductResponse(Long id, String title, Double price, String status,
                           Boolean isSaved, LocalDateTime createdDate,
                           Double totalAmount, List<PhotoEntity> photo,
                           Long sellerId, String sellerName) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.status = status;
        this.isSaved = isSaved;
        this.createdDate = createdDate;
        this.totalAmount = totalAmount;
        this.photo = photo;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
    }
}
