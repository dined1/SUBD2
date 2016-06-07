package sample.Models;

public class Theatre{
        private int Theatreid;
        private String Name;
        private String Address;
        private String Telephone;
        private String Type;


        public Theatre(int Theatreid, String Name, String Address, String Telephone, String Type){
            this.Theatreid=Theatreid;
            this.Name=Name;
            this.Address=Address;
            this.Telephone=Telephone;
            this.Type=Type;
        }

        public int getTheatreid() {
            return Theatreid;
        }

        public void setTheatreid(int theatreid) {
            Theatreid = theatreid;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getTelephone() {
            return Telephone;
        }

        public void setTelephone(String telephone) {
            Telephone = telephone;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }
    }
