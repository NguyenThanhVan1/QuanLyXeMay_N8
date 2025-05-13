package GUI;

public class IdCurrentUser {
    private static String currentUserId = "";  

    public IdCurrentUser(String id) {
        this.currentUserId = id; 
    }

    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(String userId) {
        currentUserId = userId;
    }
}
