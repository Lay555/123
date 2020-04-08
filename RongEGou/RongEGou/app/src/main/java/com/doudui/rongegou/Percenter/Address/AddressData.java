package com.doudui.rongegou.Percenter.Address;

public class AddressData {
    String id,name,pho,address, addressDetail;
    String isMoren,sfz,addressbhao,viscz;

    public AddressData( String id,String name,String pho,String address, String addressDetail,
            String isMoren,String sfz,String addressbhao,String viscz){
        this.id = id;
        this.name = name;
        this.pho = pho;
        this.address = address;
        this.addressDetail = addressDetail;
        this.isMoren = isMoren;
        this.sfz = sfz;
        this.addressbhao = addressbhao;
        this.viscz = viscz;//隐藏操作的LinearLayout
    }

    public String getViscz() {
        return viscz;
    }

    public void setViscz(String viscz) {
        this.viscz = viscz;
    }

    public String getAddressbhao() {
        return addressbhao;
    }

    public void setAddressbhao(String addressbhao) {
        this.addressbhao = addressbhao;
    }

    public String getSfz() {
        return sfz;
    }

    public void setSfz(String sfz) {
        this.sfz = sfz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPho() {
        return pho;
    }

    public void setPho(String pho) {
        this.pho = pho;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getIsMoren() {
        return isMoren;
    }

    public void setIsMoren(String isMoren) {
        this.isMoren = isMoren;
    }
}
