package org.np.tolet.domain;

import org.np.tolet.enumeration.HouseType;
import org.np.tolet.enumeration.TenantType;

import java.time.LocalDateTime;

public class Post {
    private long id;
    private String description;

    private Area area;

    private HouseType houseType;

    private TenantType tenantType;

    private User postedBy;

    private boolean isEnabled;

    private LocalDateTime addDateTime;


    public Post() {
    }

    public Post(long id, String description, Area area, HouseType houseType, TenantType tenantType, User postedBy, boolean isEnabled, LocalDateTime addDateTime) {
        this.id = id;
        this.description = description;
        this.area = area;
        this.houseType = houseType;
        this.tenantType = tenantType;
        this.postedBy = postedBy;
        this.isEnabled = isEnabled;
        this.addDateTime = addDateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public HouseType getHouseType() {
        return houseType;
    }

    public void setHouseType(HouseType houseType) {
        this.houseType = houseType;
    }

    public TenantType getTenantType() {
        return tenantType;
    }

    public void setTenantType(TenantType tenantType) {
        this.tenantType = tenantType;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public LocalDateTime getAddDateTime() {
        return addDateTime;
    }

    public void setAddDateTime(LocalDateTime addDateTime) {
        this.addDateTime = addDateTime;
    }
}
