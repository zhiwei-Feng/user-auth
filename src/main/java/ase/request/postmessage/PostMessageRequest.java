package ase.request.postmessage;

public class PostMessageRequest {

    private long posterId;
    private long articleId;
    private long targetId;
    private String content;
    private String status;
    private String timeStamp;

    public long getPosterId() {
        return posterId;
    }

    public void setPosterId(long posterId) {
        this.posterId = posterId;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
