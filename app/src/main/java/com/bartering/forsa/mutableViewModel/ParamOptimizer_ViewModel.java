package com.bartering.forsa.mutableViewModel;

import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;

import javax.inject.Inject;

public class ParamOptimizer_ViewModel implements Serializable {

    ///Sign_In
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> passsword = new MutableLiveData<>();
    private MutableLiveData<String> currentPassword = new MutableLiveData<>();
    private MutableLiveData<String> newPassword = new MutableLiveData<>();
    private MutableLiveData<String> confirmPassword = new MutableLiveData<>();
    private MutableLiveData<String> dateOfBirth = new MutableLiveData<>();

    private MutableLiveData<String> productName = new MutableLiveData<>();
    private MutableLiveData<String> productDescription = new MutableLiveData<>();
    private MutableLiveData<String> productCategory = new MutableLiveData<>();
    private MutableLiveData<String> productCategoryId = new MutableLiveData<>();
    private MutableLiveData<String> productSubCategory = new MutableLiveData<>();
    private MutableLiveData<String> productSubCategoryId = new MutableLiveData<>();

    private MutableLiveData<String> fullName = new MutableLiveData<>();
    private MutableLiveData<String> mobileNo = new MutableLiveData<>();
    private MutableLiveData<String> gender = new MutableLiveData<>();
    private MutableLiveData<String> dob = new MutableLiveData<>();
    private MutableLiveData<String> location = new MutableLiveData<>();
    private MutableLiveData<String> message = new MutableLiveData<>();

    private MutableLiveData<Float> rating = new MutableLiveData<>();
    private MutableLiveData<String> comment = new MutableLiveData<>();

    private MutableLiveData<String> houseNoFlatBlockNo = new MutableLiveData<>();
    private MutableLiveData<String> address = new MutableLiveData<>();
    private MutableLiveData<String> landmark = new MutableLiveData<>();
    private MutableLiveData<String> city = new MutableLiveData<>();
    private MutableLiveData<String> stateProvinceReligion = new MutableLiveData<>();
    private MutableLiveData<String> zipCodePostalCode = new MutableLiveData<>();
    private MutableLiveData<String> Country = new MutableLiveData<>();
    private MutableLiveData<Boolean> isTermAndConditionAccepted = new MutableLiveData<>();

    public MutableLiveData<String> getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(MutableLiveData<String> dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Inject
    public ParamOptimizer_ViewModel() {
        isTermAndConditionAccepted.setValue(false);
    }

    public MutableLiveData<Boolean> getIsTermAndConditionAccepted() {
        return isTermAndConditionAccepted;
    }

    public void setIsTermAndConditionAccepted(MutableLiveData<Boolean> isTermAndConditionAccepted) {
        this.isTermAndConditionAccepted = isTermAndConditionAccepted;
    }

    public MutableLiveData<String> getHouseNoFlatBlockNo() {
        return houseNoFlatBlockNo;
    }

    public void setHouseNoFlatBlockNo(MutableLiveData<String> houseNoFlatBlockNo) {
        this.houseNoFlatBlockNo = houseNoFlatBlockNo;
    }

    public MutableLiveData<String> getAddress() {
        return address;
    }

    public void setAddress(MutableLiveData<String> address) {
        this.address = address;
    }

    public MutableLiveData<String> getLandmark() {
        return landmark;
    }

    public void setLandmark(MutableLiveData<String> landmark) {
        this.landmark = landmark;
    }

    public MutableLiveData<String> getCity() {
        return city;
    }

    public void setCity(MutableLiveData<String> city) {
        this.city = city;
    }

    public MutableLiveData<String> getStateProvinceReligion() {
        return stateProvinceReligion;
    }

    public void setStateProvinceReligion(MutableLiveData<String> stateProvinceReligion) {
        this.stateProvinceReligion = stateProvinceReligion;
    }

    public MutableLiveData<String> getZipCodePostalCode() {
        return zipCodePostalCode;
    }

    public void setZipCodePostalCode(MutableLiveData<String> zipCodePostalCode) {
        this.zipCodePostalCode = zipCodePostalCode;
    }

    public MutableLiveData<String> getCountry() {
        return Country;
    }

    public void setCountry(MutableLiveData<String> country) {
        Country = country;
    }

    public MutableLiveData<String> getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(MutableLiveData<String> productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public MutableLiveData<String> getProductSubCategoryId() {
        return productSubCategoryId;
    }

    public void setProductSubCategoryId(MutableLiveData<String> productSubCategoryId) {
        this.productSubCategoryId = productSubCategoryId;
    }

    public MutableLiveData<String> getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(MutableLiveData<String> productCategory) {
        this.productCategory = productCategory;
    }

    public MutableLiveData<String> getProductSubCategory() {
        return productSubCategory;
    }

    public void setProductSubCategory(MutableLiveData<String> productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    public MutableLiveData<String> getProductName() {
        return productName;
    }

    public void setProductName(MutableLiveData<String> productName) {
        this.productName = productName;
    }

    public MutableLiveData<String> getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(MutableLiveData<String> productDescription) {
        this.productDescription = productDescription;
    }

    public MutableLiveData<Float> getRating() {
        return rating;
    }

    public void setRating(MutableLiveData<Float> rating) {
        this.rating = rating;
    }

    public MutableLiveData<String> getComment() {
        return comment;
    }

    public void setComment(MutableLiveData<String> comment) {
        this.comment = comment;
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public void setMessage(MutableLiveData<String> message) {
        this.message = message;
    }

    public MutableLiveData<String> getFullName() {
        return fullName;
    }

    public void setFullName(MutableLiveData<String> fullName) {
        this.fullName = fullName;
    }

    public MutableLiveData<String> getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(MutableLiveData<String> mobileNo) {
        this.mobileNo = mobileNo;
    }

    public MutableLiveData<String> getGender() {
        return gender;
    }

    public void setGender(MutableLiveData<String> gender) {
        this.gender = gender;
    }

    public MutableLiveData<String> getDob() {
        return dob;
    }

    public void setDob(MutableLiveData<String> dob) {
        this.dob = dob;
    }

    public MutableLiveData<String> getLocation() {
        return location;
    }

    public void setLocation(MutableLiveData<String> location) {
        this.location = location;
    }

    public MutableLiveData<String> getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(MutableLiveData<String> currentPassword) {
        this.currentPassword = currentPassword;
    }

    public MutableLiveData<String> getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(MutableLiveData<String> newPassword) {
        this.newPassword = newPassword;
    }

    public MutableLiveData<String> getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(MutableLiveData<String> confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(MutableLiveData<String> email) {
        this.email = email;
    }

    public MutableLiveData<String> getPasssword() {
        return passsword;
    }

    public void setPasssword(MutableLiveData<String> passsword) {
        this.passsword = passsword;
    }
}
