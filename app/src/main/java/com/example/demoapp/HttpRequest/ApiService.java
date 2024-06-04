package com.example.demoapp.HttpRequest;

import static com.example.demoapp.Utils.ApiConstant.BASE_URL;

import com.example.demoapp.Activities.admin.user.User;
import com.example.demoapp.Models.Dto.Requests.BranchRequest;
import com.example.demoapp.Models.Dto.Requests.ChangePasswordRequest;
import com.example.demoapp.Models.Dto.Requests.CreditDebitRequest;
import com.example.demoapp.Models.Dto.Requests.EnquiryRequest;
import com.example.demoapp.Models.Dto.Requests.LoanDetailRequest;
import com.example.demoapp.Models.Dto.Requests.LoginRequestDTO;
import com.example.demoapp.Models.Dto.Requests.PostCreationRequest;
import com.example.demoapp.Models.Dto.Requests.ProvinceRequest;
import com.example.demoapp.Models.Dto.Requests.SavingRequest;
import com.example.demoapp.Models.Dto.Requests.TransferRequest;
import com.example.demoapp.Models.Dto.Requests.UserCreationRequest;
import com.example.demoapp.Models.Dto.Response.AccountInfoResponse;
import com.example.demoapp.Models.Dto.Response.AuthenticationResponse;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.Response.BranchResponse;
import com.example.demoapp.Models.Dto.Response.CurrencyExchangeResponse;
import com.example.demoapp.Models.Dto.Response.InterestCalculationResponse;
import com.example.demoapp.Models.Dto.Response.PageResponse;
import com.example.demoapp.Models.Dto.Response.PostResponse;
import com.example.demoapp.Models.Dto.Response.ProvinceResponse;
import com.example.demoapp.Models.Dto.Response.UserResponse;
import com.example.demoapp.Models.Dto.Response.UserTransaction;
import com.example.demoapp.Models.Dto.entity.Comment;
import com.example.demoapp.Models.Dto.entity.Enums;
import com.example.demoapp.Models.Dto.entity.LoanDetail;
import com.example.demoapp.Models.Dto.entity.LoanDisbursement;
import com.example.demoapp.Models.Dto.entity.Post;
import com.example.demoapp.Models.Dto.entity.Saving;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    // User
    @POST("user/signup")
    Call<BaseResponse<Long>> signUp(
            @Body UserCreationRequest userCreationRequest
    );

    @POST("auth/login")
    Call<BaseResponse<AuthenticationResponse>> signIn(
            @Body LoginRequestDTO loginRequestDTO
    );

    @PUT("user/{id}")
    Call<BaseResponse<UserResponse>> updateUser(@Path("id") int id, @Body UserCreationRequest userCreationRequest);

    @GET("user/myProfile")
    Call<BaseResponse<UserResponse>> getMyProfile(@Header("Authorization") String token);

    @GET("user/update-avatar")
    Call<BaseResponse<UserResponse>> updateProfile(@Part("user_id") RequestBody userId, @Part MultipartBody.Part profilePicture);

    @DELETE("user/{userId}")
    Call<BaseResponse<UserResponse>> deleteUser(@Part("userId") RequestBody userId);


    @GET("user/list1")
    Call<BaseResponse<PageResponse<List<User>>>> getAllUsersWithSortBy1(@Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("sortBy") String sortBy);

    @GET("user/list-with-sort-by-multiple-columns")
    Call<BaseResponse<PageResponse<List<User>>>> getAllUsersWithSortByMultiColumns(@Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("sorts") String[] sorts);

    @GET("user/list-user-and-search-with-paging-and-sorting")
    Call<BaseResponse<PageResponse<List<User>>>> getAllUsersWithPagingAndSorting(@Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("search") String search, @Query("sortBy") String sortBy);

    @GET("user/advanced-search-with-criteria")
    Call<BaseResponse<List<UserResponse>>> advancedSearchWithCriteria(@Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("sortBy") String sortBy, @Query("address") String address, @Query("search") String[] search);

    @GET("user/advanced-search-with-specification")
    Call<BaseResponse<List<UserResponse>>> advancedSearchWithSpecification(@Query("page") int page, @Query("size") int size, @Query("users") String[] users, @Query("search") String[] search);

    @POST("post")
    Call<BaseResponse<PostResponse>> getPhoto(
            @Body PostCreationRequest postCreationRequest
    );

    @GET("post")
    Call<BaseResponse<List<PostResponse>>> getPosts();

    @GET("post/list-post")
    Call<BaseResponse<PageResponse<List<PostResponse>>>> getAllPostBySort(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    @GET("post/1/like")
    Call<BaseResponse<List<PostResponse>>> getPost(
            @Path("id") Integer id
    );

    @Multipart
    @POST("post")
    Call<BaseResponse<UserResponse>> upload(
            @Part MultipartBody.Part image
    );

    @GET("transaction/all")
    Call<BaseResponse<List<UserTransaction>>> notification();

    @GET("transaction/{accountID}")
    Call<BaseResponse<List<UserTransaction>>> getTransactionWithAccountID(
            @Path("accountID") Integer accountID
    );
    @GET("transaction/export")
    Call<BaseResponse> exportTransactionBetweenStartDateAndEndDate(
            @Query("accountNumber") String accountNumber,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate
    );
    @GET("transaction")
    Call<BaseResponse<List<UserTransaction>>> getTransactionBetweenStartDateAndEndDate(
            @Query("accountNumber") String accountNumber,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate
    );

    @GET("/{id}/like")
    Call<BaseResponse<String>> likePost(
            @Path("id") Integer id
    );

    @GET("/{userId}")
    Call<BaseResponse<UserResponse>> getDate(
            @Part("userId") int id
    );

    //Comment
    @GET("comment/list-comments")
    Call<BaseResponse<Comment>> getComment();

    // admin
    @GET("user/list")
    Call<BaseResponse<PageResponse<List<User>>>> getAllUsersWithSortBy(
            @Query("pageNo") int pageNo,
            @Query("pageSize") int pageSize
    );

    @GET("province")
    Call<BaseResponse<List<ProvinceResponse>>> getAllProvinces();

    @GET("branch")
    Call<BaseResponse<List<BranchResponse>>> getAllBranches();

    @GET("account/all")
    Call<BaseResponse<List<AccountInfoResponse>>> getAccountInfo(
            @Query("branchId") int branchId
    );

    @GET("account/all")
    Call<BaseResponse<List<AccountInfoResponse>>> getAccount(
    );

    @DELETE("account/{id}")
    Call<BaseResponse<Void>> deleteAccount(
            @Path("id") Integer id
    );

    @DELETE("user/{id}")
    Call<BaseResponse<Void>> deleteUser(
            @Path("id") Integer id
    );

    @DELETE("province/{id}")
    Call<BaseResponse<Void>> deleteProvince(
            @Path("id") Integer id
    );

    @DELETE("branch/delete/{id}")
    Call<BaseResponse<Void>> deleteBranch(
            @Path("id") Integer id
    );

    @GET("transaction/list-with-sort")
    Call<BaseResponse<PageResponse<List<UserTransaction>>>> getTransactionsWithSort(
            @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("sortBy") String sortBy
    );


    //Upload image
    @Multipart
    @POST("user/update-avatar")
    Call<BaseResponse<UserResponse>> uploadImage(
            @Part("user_id") RequestBody user_id,
            @Part MultipartBody.Part profilePicture
    );

    @Multipart
    @POST("post/create")
    Call<BaseResponse<com.example.demoapp.Models.Dto.entity.Post>> createPost(
            @Part("post") RequestBody post,
            @Part MultipartBody.Part file
    );

    @GET("loan-detail")
    Call<BaseResponse<List<LoanDetail>>> getAllLoanDetails();

    @GET("loan-detail/{id}")
    Call<BaseResponse<LoanDetail>> getLoanDetailById(@Path("id") int id);


    //Saving


    @GET("user/{userId}")
    Call<BaseResponse<Void>> updatePhoneToken(
            @Path("userId") int userId,
            @Query("phoneToken") String phoneToken
    );

    @GET("saving/cancel/{id}")
    Call<BaseResponse<Void>> cancelSaving(
            @Path("id") Integer id
    );


    // Province
    @POST("province/create")
    Call<BaseResponse<ProvinceResponse>> createProvince(@Body ProvinceRequest provinceRequest);

    @GET("province/{id}")
    Call<BaseResponse<ProvinceResponse>> getProvinceByID(@Path("id") int id);

    @PUT("province/{id}")
    Call<BaseResponse<ProvinceResponse>> updateProvince(@Path("id") int id, @Body ProvinceRequest province);


    // Branch
    @POST("branch/create")
    Call<BaseResponse<BranchResponse>> createBranch(@Body BranchRequest branchRequest);

    @GET("branch/{id}")
    Call<BaseResponse<BranchResponse>> getBranchById(@Path("id") int id);

    @PUT("branch/update/{id}")
    Call<BaseResponse<BranchResponse>> updateBranchInfo(@Path("id") int id, @Body BranchRequest branchRequest);


    @GET("branch/province")
    Call<BaseResponse<List<BranchResponse>>> getBranchesByProvinceId(@Query("provinceId") int provinceId);

    // Account
    @GET("account/create/{userId}/{branchInfoId}")
    Call<BaseResponse<AccountInfoResponse>> createAccount(@Path("userId") int userId, @Path("branchInfoId") int branchInfoId);

    @GET("account/user/all")
    Call<BaseResponse<List<AccountInfoResponse>>> getAllAccounts();

    @GET("account/user/{id}")
    Call<BaseResponse<AccountInfoResponse>> getAccountById(@Path("id") int id);

    @DELETE("account/user/{id}")
    Call<BaseResponse<AccountInfoResponse>> deleteAccounts(@Path("id") int id);

    @GET("account/user/{userId}")
    Call<BaseResponse<AccountInfoResponse>> getAccountByUserId(@Path("userId") int userId);

    @POST("account/balanceEnquiry")
    Call<BaseResponse<AccountInfoResponse>> balanceEnquiry(@Body EnquiryRequest request);

    @POST("account/nameEnquiry")
    Call<BaseResponse<String>> nameEnquiry(@Body EnquiryRequest request);

    @POST("account/credit")
    Call<BaseResponse<AccountInfoResponse>> creditRequest(@Body CreditDebitRequest request);

    @POST("account/debit")
    Call<BaseResponse<AccountInfoResponse>> debit(@Body CreditDebitRequest request);

    @POST("account/transfer")
    Call<BaseResponse<AccountInfoResponse>> transfer(@Body TransferRequest request);


    @POST("loan-detail")
    Call<BaseResponse<LoanDetail>> saveLoanDetail(@Body LoanDetailRequest loanDetail);

    @DELETE("loan-detail/{id}")
    Call<BaseResponse<Void>> deleteLoanDetailById(@Path("id") int id);

    @GET("loan-detail/interest/{id}")
    Call<BaseResponse<String>> interestCalculator(@Path("id") int id, @Query("type") Enums.InterestType type);

    @PUT("loan-detail/{id}/approve")
    Call<BaseResponse<Void>> approveLoanDetail(@Path("id") int id);

    @PUT("loan-detail/{id}/deny")
    Call<BaseResponse<Void>> denyLoanDetail(@Path("id") int id);

    @PUT("loan-detail/{id}/status")
    Call<BaseResponse<Void>> updateLoanDetailStatus(@Path("id") int id, @Query("status") Enums.LoanStatus status, @Query("paymentStatus") Enums.LoanPaymentStatus paymentStatus);

    @GET("loan-detail/interest/final/{id}")
    Call<BaseResponse<InterestCalculationResponse>> finalMonthlyAmountIncludingInterest(@Path("id") int id);

    @GET("loan-detail/interest/decrease/{id}")
    Call<BaseResponse<List<InterestCalculationResponse>>> decreaseInterestCalculation(@Path("id") int id);

    //Comment
    @GET("comment/create")
    Call<BaseResponse<Comment>> createComment(
            @Body Long postId,
            @Body String author,
            @Body String content
    );

    //Saving
    @POST("saving/create")
    Call<BaseResponse<Saving>> createSavingAccount(
            @Body SavingRequest savingRequest
    );

    @GET("saving/{id}")
    Call<BaseResponse<Saving>> getSaving(
            @Path("id") Integer id
    );

    @GET("cancel/{savingId}")
    Call<BaseResponse<Void>> cancelSavingAccount(
            @Path("savingId") int savingId
    );

    @GET("refund")
    Call<BaseResponse<?>> refundSavingAccount();

    @GET("/{userId}")
    Call<BaseResponse<Saving>> getSavingByUserId(
            @Path("userId") Integer userId
    );

    @DELETE("loan-disbursement/{id}")
    Call<BaseResponse<Void>> deleterLoanDisbursements(
            @Path("id") Integer id
    );

    @GET("loan-disbursement")
    Call<BaseResponse<List<LoanDisbursement>>> getLoanDisbursements();

    @GET("forgot-password/verify-email/{email}")
    Call<BaseResponse<Void>> verifyEmail(@Path("email") String email);

    @GET("forgot-password/verify-otp/{otp}/{email}")
    Call<BaseResponse<Void>> verifyOTP(@Path("otp") int otp, @Path("email") String email);

    @POST("forgot-password/reset-password/{email}")
    Call<BaseResponse<Void>> resetPassword(@Path("email") String email, @Body ChangePasswordRequest changePasswordRequest);
}
