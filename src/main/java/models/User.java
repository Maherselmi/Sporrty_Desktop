package models;

public class User {

        private int id;
        private String nom;
        private String prenom;
        private String email;
        private String password;
        private Role role;
private String image_user;

    public void setImage_user(String image_user) {
        this.image_user = image_user;
    }

    public User(int id, String nom, String prenom, String email, String password, Role role, String image_user) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.image_user = image_user;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", image_user='" + image_user + '\'' +
                '}';
    }

    // Constructeur par défaut
        public User() {
        }
    public User(int id) {
        this.id = id;
    }
        // Constructeur avec tous les champs
        public User(int id, String nom, String prenom, String email, String password, Role role) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.email = email;
            this.password = password;
            this.role = role;
        }

    public User( String nom, String prenom, String email, String password, Role role) {

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public User( int id,String nom, String prenom, String email, String password) {
this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;

    }

    public User(String Nom, String Prenom, String Email) {
            this.nom=Nom;
            this.prenom=Prenom;
            this.email=Email;
    }

    public User(String nom, String prenom, String email, String password, Role role, String image_user) {

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.image_user=image_user;
        }


    // Méthodes getters et setters pour tous les champs
        public int getId() {
            return id;
        }


        public void setId(int id) {
            this.id = id;
        }

        public String getNom() {
            return nom;
        }


        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

    public String getImage_user() {
        return image_user;
    }


}
