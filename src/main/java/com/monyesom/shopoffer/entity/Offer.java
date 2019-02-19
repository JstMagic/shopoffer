package com.monyesom.shopoffer.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "offer")
public class Offer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated product ID", hidden = true)
    @Column(name = "offer_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "offer_name")
    @ApiModelProperty(notes = "What you will like the offer to be called")
    private String offerName;

    @Column(name = "product_name")
    @NotNull
    @ApiModelProperty(notes = "The product name to create an offer for", required = true)
    private String productName;

    @Column(name = "offer_price")
    @NotNull
    @ApiModelProperty(notes = "The price of the product", required = true)
    private BigDecimal offerPrice;

    @NotNull
    @Column(name = "expiring_date", nullable = false)
    @ApiModelProperty(notes = "When the offer will expire", required = true)
    private Date expiringDate;

    @Column(name = "valid")
    @NotNull
    @ApiModelProperty(notes = "This will be checked if offer is no longer valid", hidden = true, value = "true")
    private boolean valid;

    @Column(name = "cancelled")
    @NotNull
    @ApiModelProperty(notes = "This will be checked if offer was manually cancelled", value = "false")
    boolean cancelled;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    @ApiModelProperty(notes = "When the offer was created, this will be auto-populated")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    @ApiModelProperty(notes = "When the offer was updated, this will be auto-populated")
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    //date of last entries or updated entries
    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }
}
