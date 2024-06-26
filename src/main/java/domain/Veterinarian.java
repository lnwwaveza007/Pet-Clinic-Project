package domain;

import java.io.Serializable;

public class Veterinarian implements Serializable {

        private int idCard;
        private String password;
        private String name;
        private String address;
        private String phone;

        public Veterinarian(int idCard, String password, String name, String address, String phone) {
            this.idCard = idCard;
            this.password = password;
            this.name = name;
            this.address = address;
            this.phone = phone;
        }

        public int getIdCard() {
            return idCard;
        }

        public void setIdCard(int idCard) {
            this.idCard = idCard;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
}
