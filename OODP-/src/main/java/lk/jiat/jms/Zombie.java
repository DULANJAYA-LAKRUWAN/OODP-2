package lk.jiat.jms;

public class Zombie implements Prototype {
    private String type;
    private int attackPower;
    private int health;

    public Zombie(String type, int attackPower, int health) {
        this.type = type;
        this.attackPower = attackPower;
        this.health = health;
    }

    // Copy Constructor for cloning
    public Zombie(Zombie other) {
        this.type = other.type;
        this.attackPower = other.attackPower;
        this.health = other.health;
    }

    @Override
    public Zombie clone() {
        return new Zombie(this);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void display() {
        System.out.println("----------------------------------------");
        System.out.println(" Zombie Type   : " + type);
        System.out.println(" Attack Power  : " + attackPower);
        System.out.println(" Health        : " + health);
        System.out.println(" HashCode (ID) : " + Integer.toHexString(System.identityHashCode(this)));
        System.out.println("----------------------------------------");
    }
}
