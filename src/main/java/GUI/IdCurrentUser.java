package GUI;

public class IdCurrentUser {
    private static int currentUserId = 1;  

    public IdCurrentUser(int id) {
        this.currentUserId = id; 
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int userId) {
        currentUserId = userId;
    }
}
