package tw.gov.bli.common.util;

public class MgFile {
    private String type;
    private String name;
    private Integer parentId;
    private String pathToken;
    private String description;
    private Integer status;
    private String createDate;
    private Integer holdTime;
    private Long size;
    private String id;
    private String uploadTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPathToken() {
        return pathToken;
    }

    public void setPathToken(String pathToken) {
        this.pathToken = pathToken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(Integer holdTime) {
        this.holdTime = holdTime;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}
