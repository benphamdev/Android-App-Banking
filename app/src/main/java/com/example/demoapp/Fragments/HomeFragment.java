package com.example.demoapp.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.demoapp.Activities.ChuyenTien.ChuyenTienMat;
import com.example.demoapp.Activities.ChuyenTien.ChuyenTienMatTaijVNPOST;
import com.example.demoapp.Activities.ChuyenTien.ChuyenTienNgoaiVCB;
import com.example.demoapp.Activities.ChuyenTien.ChuyenTienNhanh24_7NgoaiVCB;
import com.example.demoapp.Activities.ChuyenTien.ChuyenTienTrongVCB;
import com.example.demoapp.Activities.ChuyenTien.ChuyenTienTuThien;
import com.example.demoapp.Activities.ChuyenTien.LapYeuCauChuyenTien;
import com.example.demoapp.Activities.ChuyenTien.QuaTang;
import com.example.demoapp.Activities.ChuyenTien.QuanLyYeuCauChuyenTien;
import com.example.demoapp.Activities.ChuyenTien.RutTienMatTaiVNPOST;
import com.example.demoapp.Activities.ChuyenTien.TrangThaiChuyenTien;
import com.example.demoapp.Activities.DichVuThe.DangKyChuyenDoiCongNgheTheGhiNo;
import com.example.demoapp.Activities.DichVuThe.DangKyGiaHanThe;
import com.example.demoapp.Activities.DichVuThe.DangKyGiaHanTheTinDung;
import com.example.demoapp.Activities.DichVuThe.DangKyPhatHanhTheGhiNo;
import com.example.demoapp.Activities.DichVuThe.PhatHanhThePhuTheGhiNo;
import com.example.demoapp.Activities.DichVuThe.ThanhToanChoChuTheVCBKhac;
import com.example.demoapp.Activities.DichVuThe.ThanhToanTheTinDung;
import com.example.demoapp.Activities.DichVuThe.TraCuuSaoKeThe;
import com.example.demoapp.Activities.NapTien.DangKyNapTienTuDong;
import com.example.demoapp.Activities.NapTien.HuyDangKy;
import com.example.demoapp.Activities.NapTien.NapTienDaiLy;
import com.example.demoapp.Activities.NapTien.NapTienDienThoai;
import com.example.demoapp.Activities.NapTien.NapTienTKGiaoThong;
import com.example.demoapp.Activities.NapTien.NapTienViDienTu;
import com.example.demoapp.Activities.Photo;
import com.example.demoapp.Activities.SignIn;
import com.example.demoapp.Activities.ThanhToanHoaDon.CaiDatHinhThucThanhToanSaoKe;
import com.example.demoapp.Activities.ThanhToanHoaDon.CuocDiDongTraSau;
import com.example.demoapp.Activities.ThanhToanHoaDon.CuocDienThoaiCoDinh;
import com.example.demoapp.Activities.ThanhToanHoaDon.CuocInternetADSL;
import com.example.demoapp.Activities.ThanhToanHoaDon.CuocTruyenHinhCap;
import com.example.demoapp.Activities.ThanhToanHoaDon.DangKiSuDungTheTrenInternet;
import com.example.demoapp.Activities.ThanhToanHoaDon.DangKyDichVu;
import com.example.demoapp.Activities.ThanhToanHoaDon.DangKySuDungTheTaiPOSATMNuocNgoai;
import com.example.demoapp.Activities.ThanhToanHoaDon.HoaDonCuocVienThongVNPT;
import com.example.demoapp.Activities.ThanhToanHoaDon.HoaDonTienDien;
import com.example.demoapp.Activities.ThanhToanHoaDon.HoaDonTienNuoc;
import com.example.demoapp.Activities.ThanhToanHoaDon.HuyDangKySuDungTheTaiPOSATMNuocNgoai;
import com.example.demoapp.Activities.ThanhToanHoaDon.HuyDangKySuDungTheTrenInternet;
import com.example.demoapp.Activities.ThanhToanHoaDon.KhoaThe;
import com.example.demoapp.Activities.ThanhToanHoaDon.KichHoatThe;
import com.example.demoapp.Activities.ThanhToanHoaDon.MoKhoaThe;
import com.example.demoapp.Activities.ThanhToanHoaDon.NgungDichVu;
import com.example.demoapp.Activities.ThanhToanHoaDon.PhiDichVuChungCu;
import com.example.demoapp.Activities.ThanhToanHoaDon.TaoMoiDoiMaPIN;
import com.example.demoapp.Activities.ThanhToanHoaDon.ThanhToanHocPhi;
import com.example.demoapp.Activities.ThanhToanHoaDon.ThanhToanKhoanVayTaiChinh;
import com.example.demoapp.Activities.ThanhToanHoaDon.ThanhToanPhiLogistic;
import com.example.demoapp.Activities.ThanhToanHoaDon.ThanhToanVienPhi;
import com.example.demoapp.Activities.ThanhToanHoaDon.ThayDoiHanMucNgayCuaThe;
import com.example.demoapp.Activities.ThanhToanHoaDon.ThayDoiTaiKhoanThanhToanChiDinh;
import com.example.demoapp.Activities.ThanhToanHoaDon.TienThuePhiDichVuKhac;
import com.example.demoapp.Activities.TienIch.Convert;
import com.example.demoapp.Activities.TienIch.DatLichHen;
import com.example.demoapp.Activities.TienIch.DatNickNameTaiKhoan;
import com.example.demoapp.Activities.TienIch.GioiThieuBan;
import com.example.demoapp.Activities.TienIch.HanMucChuyenTien;
import com.example.demoapp.Activities.TienIch.KichHoatVCBSmartOTP;
import com.example.demoapp.Activities.TienIch.LapYeuCauTraSoat;
import com.example.demoapp.Activities.TienIch.LichSuDatLichHen;
import com.example.demoapp.Activities.TienIch.QuanLyNhom;
import com.example.demoapp.Activities.TienIch.TraCuuYeuCauTraSoat;
import com.example.demoapp.Activities.TienIch.UuDai;
import com.example.demoapp.Activities.TietKiem.MoTietKiem;
import com.example.demoapp.Activities.TietKiem.NopThemVaoTietKiem;
import com.example.demoapp.Activities.TietKiem.RutTienTuTietKiem;
import com.example.demoapp.Activities.TietKiem.TatToanTietKiem;
import com.example.demoapp.Activities.TinDung.TatToanVayKhac;
import com.example.demoapp.Activities.TinDung.ThanhToanKhoanVay;
import com.example.demoapp.Activities.TinDung.VayThauChiTatToan;
import com.example.demoapp.Activities.TinDung.VayTrucTuyenCamCoTienGui;
import com.example.demoapp.Activities.admin.AdminsActivity;
import com.example.demoapp.Adapters.PhotpViewPager2Adapter;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.AccountInfoResponse;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.Response.PostResponse;
import com.example.demoapp.Models.Dto.Response.UserResponse;
import com.example.demoapp.Models.Dto.sharePreferences.SharePreferencesManager;
import com.example.demoapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ShimmerFrameLayout shimmerFrameLayout;
    Handler handler;
    View view;
    private Toolbar toolbar;
    private LinearLayout layoutChuyenTienCardview1, layoutThanhToanHoaDonCardview1,
            layoutNapTienCardview1, layoutDichVuTheCardview1,
            layoutTinDungCardview1, layoutTietKiemCardview1, layoutDichVuBaoHiemCardview1,
            layoutDauTuCardview1, layoutNganSachNhaNuocCardview1,
            layoutTienIchCardview1, layoutMuaSamCardview1;
    private CardView layoutChuyenTienCardview, layoutThanhToanHoaDonCardview, layoutNapTienCardview,
            layoutDichVuTheCardview,
            layoutTinDungCardview, layoutTietKiemCardview, layoutDichVuBaoHiemCardview,
            layoutDauTuCardview, layoutNganSachNhaNuocCardview,
            layoutTienIchCardview, layoutMuaSamCardview;
    private TextView tvCopy;
    private ImageView imgCopy;
    private BottomNavigationView navigationView;
    private ImageButton imgbtnDanhSachTaiKhoan;
    private ImageButton imgbtnChuyentientrongVCB, imgbtnChuyenTien247, imgbtnChuyenTienNgoaiVCB,
            imgbtnChuyenTienMat, imgbtnChuyenTienTuThien, imgQuaTang, imgbtnTrangThaiChuyenTien,
            imgbtnChuyenTienDiNuocNgoai, imgbtnRutChuyenTienTaiVNPOST;
    private ImageButton imgbtnHoaDonTienDien, imgbtnHoaDonTienNuoc, imgbtnCuocDDTraSau,
            imgCuocInternetADSL, imgbtnThanhToanVienPhi, imgbtnThanhToanHocPhi,
            imgbtnThanhToanLogistic, imgbtnVCBAutoDebit, imgbtnCacDichVuKhac, imgbtnDichVuTheKhac;
    private ImageButton imgbtnNapTienDienThoai, imgbtnNapTienViDienTu, imgbtnNapTienDaiLy,
            imgbtnNapTienTKGiaoThong, imgbtnDichVuNapTienTuDong;
    private ImageButton imgbtnThanhToanKhoanVay, imgbtnTatToanKhoanVay, imgbtnKhoiTaokhoanVay;
    private ImageButton imgbtnTraCuuSaoKe, imgbtnGiaHanThe, imgbtnPhatHanhChuyenDoi,
            imgbtnThanhToanTheTinDung;
    private ImageButton imgbtnQuanLyNhom, imgbtnTraSoatTrucTuyen, imgbtnKichHoatSmartOTP,
            imgbtnHanMucChuyenTien, imgbtnVCBRewards, imgbtnUuDai, imgbtnGioiThieuBanBe,
            imgbtnVCBFamily, imgbtnDatNicknameTaiKhoan, imgbtnDatLichHenVoiVCB, imgbtnConvert,
            imgbtnTienIchKhac;
    private ImageButton imgbtnDatHoa, imgbtnDatVeXemPhim, imgbtnDatXe, imgbtnDatVeMayBay,
            imgbtnDatPhongKhachSan, imgbtnDatVeTau, imgbtnVNPTaxi, imgbtnMuaSamVNShop;
    private ImageButton imgbtnTietkiemThuong;
    private CircleImageView imgCircleProfile;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        AnhXa();
        Toolbar toolbar = view.findViewById(R.id.tool_bar_home);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }

        ImageView backButton = view.findViewById(R.id.toolbar_back_home);
        Drawable drawable = backButton.getDrawable();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(
                    drawable,
                    ContextCompat.getColor(requireContext(), R.color.white)
            );
            backButton.setImageDrawable(drawable);
        }

        backButton.setOnClickListener(v -> startActivity(new Intent(
                requireActivity(),
                SignIn.class
        )));
        layoutChuyenTienCardview.setOnClickListener(v -> {
            if (layoutChuyenTienCardview1.getVisibility() == View.GONE) {
                layoutChuyenTienCardview1.setVisibility(View.VISIBLE);
            } else {
                layoutChuyenTienCardview1.setVisibility(View.GONE);
            }
        });
        layoutThanhToanHoaDonCardview.setOnClickListener(v -> {
            if (layoutThanhToanHoaDonCardview1.getVisibility() == View.GONE) {
                layoutThanhToanHoaDonCardview1.setVisibility(View.VISIBLE);
            } else {
                layoutThanhToanHoaDonCardview1.setVisibility(View.GONE);
            }
        });
        layoutNapTienCardview.setOnClickListener(v -> {
            if (layoutNapTienCardview1.getVisibility() == View.GONE) {
                layoutNapTienCardview1.setVisibility(View.VISIBLE);
            } else {
                layoutNapTienCardview1.setVisibility(View.GONE);
            }
        });
        layoutDichVuTheCardview.setOnClickListener(v -> {
            if (layoutDichVuTheCardview1.getVisibility() == View.GONE) {
                layoutDichVuTheCardview1.setVisibility(View.VISIBLE);
            } else {
                layoutDichVuTheCardview1.setVisibility(View.GONE);
            }
        });
        layoutTinDungCardview.setOnClickListener(v -> {
            if (layoutTinDungCardview1.getVisibility() == View.GONE) {
                layoutTinDungCardview1.setVisibility(View.VISIBLE);
            } else {
                layoutTinDungCardview1.setVisibility(View.GONE);
            }
        });
        layoutTietKiemCardview.setOnClickListener(v -> {
            if (layoutTietKiemCardview1.getVisibility() == View.GONE) {
                layoutTietKiemCardview1.setVisibility(View.VISIBLE);
            } else {
                layoutTietKiemCardview1.setVisibility(View.GONE);
            }
        });
        layoutDichVuBaoHiemCardview.setOnClickListener(v -> {
            if (layoutDichVuBaoHiemCardview1.getVisibility() == View.GONE) {
                layoutDichVuBaoHiemCardview1.setVisibility(View.VISIBLE);
            } else {
                layoutDichVuBaoHiemCardview1.setVisibility(View.GONE);
            }
        });
        layoutDauTuCardview.setOnClickListener(v -> {
            if (layoutDauTuCardview1.getVisibility() == View.GONE) {
                layoutDauTuCardview1.setVisibility(View.VISIBLE);
            } else {
                layoutDauTuCardview1.setVisibility(View.GONE);
            }
        });
        layoutNganSachNhaNuocCardview.setOnClickListener(v -> {
            if (layoutNganSachNhaNuocCardview1.getVisibility() == View.GONE) {
                layoutNganSachNhaNuocCardview1.setVisibility(View.VISIBLE);
            } else {
                layoutNganSachNhaNuocCardview1.setVisibility(View.GONE);
            }
        });
        layoutTienIchCardview.setOnClickListener(v -> {
            if (layoutTienIchCardview1.getVisibility() == View.GONE) {
                layoutTienIchCardview1.setVisibility(View.VISIBLE);
            } else {
                layoutTienIchCardview1.setVisibility(View.GONE);
            }
        });
        layoutMuaSamCardview.setOnClickListener(v -> {
            if (layoutMuaSamCardview1.getVisibility() == View.GONE) {
                layoutMuaSamCardview1.setVisibility(View.VISIBLE);
            } else {
                layoutMuaSamCardview1.setVisibility(View.GONE);
            }
        });
        shimmerFrameLayout.startShimmer();
        handler = new Handler();
        handler.postDelayed(() -> {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            layoutChuyenTienCardview.setVisibility(View.VISIBLE);
            layoutThanhToanHoaDonCardview.setVisibility(View.VISIBLE);
            layoutNapTienCardview.setVisibility(View.VISIBLE);
            layoutDichVuTheCardview.setVisibility(View.VISIBLE);
            layoutTinDungCardview.setVisibility(View.VISIBLE);
            layoutTietKiemCardview.setVisibility(View.VISIBLE);
            layoutDichVuBaoHiemCardview.setVisibility(View.VISIBLE);
            layoutDauTuCardview.setVisibility(View.VISIBLE);
            layoutNganSachNhaNuocCardview.setVisibility(View.VISIBLE);
            layoutTienIchCardview.setVisibility(View.VISIBLE);
            layoutMuaSamCardview.setVisibility(View.VISIBLE);
        }, 3000);
        api();
        chuyenActivity();
        xuLyCopy();
        getPhoto();
        return view;
    }

    private void api() {

//        UserProfile
        TextView tvXinChao = view.findViewById(R.id.tv_xinchao);

        Context context = getActivity();
        SharePreferencesManager sharePreferencesManager = new SharePreferencesManager(context);
        String tokenApi = sharePreferencesManager.getToken();
        SharePreferencesManager manager = new SharePreferencesManager(context);
        String tokenApp = manager.getTokenApp();
        SharePreferencesManager manager1 = new SharePreferencesManager(context);

        int id = manager1.getUserId();

        Log.d("TOKENNNNNN", tokenApp);

        ApiService.apiService.updatePhoneToken(id, tokenApp)
                .enqueue(new Callback<BaseResponse<Void>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<Void>> call,
                            Response<BaseResponse<Void>> response
                    ) {
                        if (response.isSuccessful()) {
                            Log.d(
                                    "SUCCESS",
                                    "tokenApp: " + response.body()
                                            .toString()
                            );
                        } else {
                            try {
                                Log.d("TAG", "tokenApp: " + response.errorBody()
                                        .string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BaseResponse<Void>> call, Throwable throwable
                    ) {
                        Log.e("E:", throwable.getMessage());
                    }
                });

        ApiService.apiService.getMyProfile("Bearer " + tokenApi)
                .enqueue(new Callback<BaseResponse<UserResponse>>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(
                            @NonNull Call<BaseResponse<UserResponse>> call,
                            @NonNull Response<BaseResponse<UserResponse>> response
                    ) {
                        if (response.isSuccessful()) {
                            Log.d(
                                    "SUCCESS",
                                    "onResponse: " + response.body()
                                            .toString()
                            );
                            UserResponse userResponse = response.body()
                                    .getData();

                            // set full name for user
                            tvXinChao.setText("Xin Chào " +
                                    userResponse.getFirstName() + " " +
                                    userResponse.getLastName() + " " +
                                    userResponse.getOtherName());

                            //  set image avatar for user
                            if (userResponse.getProfilePicture() != null) {
                                String avatar = userResponse.getProfilePicture();
                                Glide.with(HomeFragment.this)
                                        .load(avatar)
                                        .into(imgCircleProfile);
                            }

                            // save userid in SharePreferencesManager
                            SharePreferencesManager userId =
                                    new SharePreferencesManager(context);

                            userId.saveUserId(response.body()
                                    .getData()
                                    .getId());

                        } else {
                            try {
                                Log.d("TAG", "onResponse: " + response.errorBody()
                                        .string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<BaseResponse<UserResponse>> call,
                            @NonNull Throwable throwable
                    ) {
                        Log.e("E:", Objects.requireNonNull(throwable.getMessage()));
                    }
                });

//        Account
        TextView tv_so_tai_khoan = view.findViewById(R.id.tv_so_tai_khoan);
        TextView tv_tien_tai_khoan = view.findViewById(R.id.tv_tien_tai_khoan);
        ImageView imv_on_off = view.findViewById(R.id.imv_on_off);

        ApiService.apiService.getAccountByUserId(id)
                .enqueue(new Callback<BaseResponse<AccountInfoResponse>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<AccountInfoResponse>> call,
                            Response<BaseResponse<AccountInfoResponse>> response
                    ) {
                        if (response.isSuccessful()) {
                            Log.d(
                                    "SUCCESS",
                                    "ACCOUNT: " + response.body()
                                            .toString()
                            );
                            BaseResponse<AccountInfoResponse> baseResponse =
                                    response.body();
                            if (baseResponse != null && baseResponse.getData() != null) {
                                Log.d(
                                        "SUCCESS",
                                        "onResponse: " + baseResponse
                                );
                                AccountInfoResponse data = baseResponse.getData();
                                SharePreferencesManager stk =
                                        new SharePreferencesManager(context);
                                stk.saveStk(data.getAccountNumber());
                                SharePreferencesManager manager2 =
                                        new SharePreferencesManager(context);
                                int s = Integer.parseInt(response.body()
                                                                 .getData()
                                                                 .getId());
                                manager2.saveAccountId(s);
                                tv_so_tai_khoan.setText(data.getAccountNumber());
                                tv_tien_tai_khoan.setText(String.valueOf(data.getAccountBalance()));
                                tv_tien_tai_khoan.setVisibility(View.GONE);
                            }

                        } else {
                            try {
                                Log.d("TAG", "onResponse: " + response.errorBody()
                                        .string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BaseResponse<AccountInfoResponse>> call,
                            Throwable throwable
                    ) {
                        Log.e("E:", throwable.getMessage());
                    }
                });

        // Thiết lập lắng nghe sự kiện cho ImageView
        imv_on_off.setOnClickListener(v -> {
            // Kiểm tra trạng thái của TextView
            if (tv_tien_tai_khoan.getVisibility() == View.VISIBLE) {
                // Nếu TextView đang hiển thị, ẩn nó
                tv_tien_tai_khoan.setVisibility(View.GONE);
            } else {
                // Nếu TextView đang ẩn, hiển thị nó
                tv_tien_tai_khoan.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    public void chuyenActivity() {
        imgbtnTietkiemThuong.setOnClickListener(v -> openFeedbackDialogTietKiem());
        imgbtnTraCuuSaoKe.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                TraCuuSaoKeThe.class
        )));
        imgbtnChuyentientrongVCB.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                ChuyenTienTrongVCB.class
        )));
        imgbtnChuyenTien247.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                ChuyenTienNhanh24_7NgoaiVCB.class
        )));
        imgbtnChuyenTienNgoaiVCB.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                ChuyenTienNgoaiVCB.class
        )));
        imgbtnChuyenTienMat.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                ChuyenTienMat.class
        )));
        imgbtnChuyenTienTuThien.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                ChuyenTienTuThien.class
        )));
        imgQuaTang.setOnClickListener(v -> startActivity(new Intent(getActivity(), QuaTang.class)));
        imgbtnTrangThaiChuyenTien.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                TrangThaiChuyenTien.class
        )));
        imgbtnHoaDonTienDien.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                HoaDonTienDien.class
        )));
        imgbtnHoaDonTienNuoc.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                HoaDonTienNuoc.class
        )));
        imgbtnCuocDDTraSau.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                CuocDiDongTraSau.class
        )));
        imgCuocInternetADSL.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                CuocInternetADSL.class
        )));
        imgbtnThanhToanVienPhi.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                ThanhToanVienPhi.class
        )));
        imgbtnThanhToanHocPhi.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                ThanhToanHocPhi.class
        )));
        imgbtnThanhToanLogistic.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                ThanhToanPhiLogistic.class
        )));
        imgbtnNapTienDienThoai.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                NapTienDienThoai.class
        )));
        imgbtnNapTienViDienTu.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                NapTienViDienTu.class
        )));
        imgbtnNapTienDaiLy.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                NapTienDaiLy.class
        )));
        imgbtnNapTienTKGiaoThong.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                NapTienTKGiaoThong.class
        )));
        imgbtnThanhToanKhoanVay.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                ThanhToanKhoanVay.class
        )));
        imgbtnChuyenTienDiNuocNgoai.setOnClickListener(v -> openFeedbackDialog());
        imgbtnRutChuyenTienTaiVNPOST.setOnClickListener(v -> openFeedbackDialogVNPOST());
        imgbtnVCBAutoDebit.setOnClickListener(v -> openFeedbackDialogVCBAutoDebit());
        imgbtnDichVuNapTienTuDong.setOnClickListener(v -> openFeedbackDialogDangKiNaptienTuDong());
        imgbtnGiaHanThe.setOnClickListener(v -> openFeedbackDialogGiaHanThe());
        imgbtnPhatHanhChuyenDoi.setOnClickListener(v -> openFeedbackDialogPhatHanhChuyenDoiThe());
        imgbtnThanhToanTheTinDung.setOnClickListener(v -> openFeedbackDialogThanhToanTheTinDung());
        imgbtnTatToanKhoanVay.setOnClickListener(v -> openFeedbackDialogTatToanKhoanVay());
        imgbtnKhoiTaokhoanVay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialogKhoiTaoKhoanVay();
            }
        });
        imgbtnCacDichVuKhac.setOnClickListener(v -> openFeedbackDialogCacDichVuKhacHoaDon());
        imgbtnDichVuTheKhac.setOnClickListener(v -> openFeedbackDialogDichVuTheKhac());
        imgbtnQuanLyNhom.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                QuanLyNhom.class
        )));
        imgbtnTraSoatTrucTuyen.setOnClickListener(v -> openFeedbackDialogTraSoatTrucTuyen());
        imgbtnKichHoatSmartOTP.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                KichHoatVCBSmartOTP.class
        )));
        imgbtnHanMucChuyenTien.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                HanMucChuyenTien.class
        )));
        imgbtnVCBRewards.setOnClickListener(v -> showDialog());
        imgbtnDatHoa.setOnClickListener(v -> hoaDialog());
        imgbtnDatPhongKhachSan.setOnClickListener(v -> hotelDialog());
        imgbtnDatVeMayBay.setOnClickListener(v -> airplaneDialog());
        imgbtnDatVeTau.setOnClickListener(v -> tauDialog());
        imgbtnDatVeXemPhim.setOnClickListener(v -> phimDialog());
        imgbtnVNPTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taxiDialog();
            }
        });
        imgbtnMuaSamVNShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vnshopDialog();
            }
        });
        imgbtnDatXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xeDialog();
            }
        });
        imgbtnUuDai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UuDai.class));
            }
        });
        imgbtnGioiThieuBanBe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GioiThieuBan.class));
            }
        });
        imgbtnVCBFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        imgbtnDatNicknameTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DatNickNameTaiKhoan.class));
            }
        });
        imgbtnDatLichHenVoiVCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialogDatLichHenVoiVCB();
            }
        });
        imgbtnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Convert.class));
            }
        });

        imgbtnDanhSachTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TraCuuSaoKeThe.class));
            }
        });
        imgCircleProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment = new ProfileFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, profileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void AnhXa() {
        toolbar = view.findViewById(R.id.tool_bar_home);
        imgbtnChuyentientrongVCB = view.findViewById(R.id.img_trongvcb);
        imgbtnChuyenTien247 = view.findViewById(R.id.img_nhanh_24);
        imgbtnChuyenTienNgoaiVCB = view.findViewById(R.id.img_ngoaivcb);
        imgbtnChuyenTienMat = view.findViewById(R.id.img_tien_mat);
        imgbtnChuyenTienTuThien = view.findViewById(R.id.img_tu_thien);
        imgQuaTang = view.findViewById(R.id.img_qua_tang);
        imgbtnTrangThaiChuyenTien = view.findViewById(R.id.img_trang_thai);
        imgbtnHoaDonTienDien = view.findViewById(R.id.img_dien);
        imgbtnHoaDonTienNuoc = view.findViewById(R.id.img_nuoc);
        imgbtnCuocDDTraSau = view.findViewById(R.id.img_cuoc_di_dong);
        imgCuocInternetADSL = view.findViewById(R.id.img_internet);
        imgbtnThanhToanVienPhi = view.findViewById(R.id.img_vien_phi);
        imgbtnThanhToanHocPhi = view.findViewById(R.id.img_hoc_phi);
        imgbtnThanhToanLogistic = view.findViewById(R.id.img_logistic);
        imgbtnNapTienDienThoai = view.findViewById(R.id.img_dien_thoai);
        imgbtnNapTienDaiLy = view.findViewById(R.id.img_dai_ly);
        imgbtnNapTienViDienTu = view.findViewById(R.id.img_vi_dien_tu);
        imgbtnNapTienTKGiaoThong = view.findViewById(R.id.img_giao_thong);
        imgbtnThanhToanKhoanVay = view.findViewById(R.id.img_thanh_toan_khoan_vay);
        imgbtnChuyenTienDiNuocNgoai = view.findViewById(R.id.img_nuoc_ngoai);
        imgbtnRutChuyenTienTaiVNPOST = view.findViewById(R.id.img_vnpost);
        imgbtnVCBAutoDebit = view.findViewById(R.id.img_auto_debit);
        imgbtnDichVuNapTienTuDong = view.findViewById(R.id.img_tu_dong);
        imgbtnTraCuuSaoKe = view.findViewById(R.id.img_tra_cuu);
        imgbtnGiaHanThe = view.findViewById(R.id.img_gia_han);
        imgbtnPhatHanhChuyenDoi = view.findViewById(R.id.img_phat_hanh);
        imgbtnThanhToanTheTinDung = view.findViewById(R.id.img_thanh_toan_the_tin_dung);
        imgbtnTatToanKhoanVay = view.findViewById(R.id.img_tat_toan_khoan_vay);
        imgbtnKhoiTaokhoanVay = view.findViewById(R.id.img_khoi_tao_khoan_vay);
        imgbtnCacDichVuKhac = view.findViewById(R.id.img_dich_vu_khac);
        imgbtnDichVuTheKhac = view.findViewById(R.id.img_dich_vu_the_khac);
        imgbtnQuanLyNhom = view.findViewById(R.id.img_quan_ly_nhom);
        imgbtnTraSoatTrucTuyen = view.findViewById(R.id.img_tra_soat_truc_tuyen);
        imgbtnKichHoatSmartOTP = view.findViewById(R.id.img_kich_hoat_smart_otp);
        imgbtnHanMucChuyenTien = view.findViewById(R.id.img_han_muc_chuyen_tien);
        imgbtnVCBRewards = view.findViewById(R.id.img_vcb_rewards);
        imgbtnUuDai = view.findViewById(R.id.img_uu_dai);
        imgbtnGioiThieuBanBe = view.findViewById(R.id.img_gioi_thieu_ban_be);
        imgbtnVCBFamily = view.findViewById(R.id.img_vcb_family);
        imgbtnDatNicknameTaiKhoan = view.findViewById(R.id.img_dat_nickname_tai_khoan);
        imgbtnDatLichHenVoiVCB = view.findViewById(R.id.img_dat_lich_hen_voi_vcb);
        imgbtnConvert = view.findViewById(R.id.img_convert);
        imgbtnTienIchKhac = view.findViewById(R.id.img_tien_ich_khac);
        imgbtnMuaSamVNShop = view.findViewById(R.id.img_mua_sam_vnshop);
        imgbtnDatPhongKhachSan = view.findViewById(R.id.img_dat_phong_khach_san);
        imgbtnVNPTaxi = view.findViewById(R.id.img_vnpay_taxi);
        imgbtnDatVeXemPhim = view.findViewById(R.id.img_dat_ve_xem_phim);
        imgbtnDatVeTau = view.findViewById(R.id.img_dat_ve_tau);
        imgbtnDatXe = view.findViewById(R.id.img_dat_ve_xe);
        imgbtnDatHoa = view.findViewById(R.id.img_dat_hoa);
        imgbtnDatVeMayBay = view.findViewById(R.id.img_dat_ve_may_bay);
        imgbtnConvert = view.findViewById(R.id.img_convert);
        navigationView = view.findViewById(R.id.bottomnavigation);
        tvCopy = view.findViewById(R.id.tv_so_tai_khoan);
        imgCopy = view.findViewById(R.id.img_copy);
        imgbtnDanhSachTaiKhoan = view.findViewById(R.id.img_credit);
        imgbtnTietkiemThuong = view.findViewById(R.id.img_tiet_kiem_thuong);
        imgCircleProfile = view.findViewById(R.id.circleImageViewProfile);
        layoutChuyenTienCardview = view.findViewById(R.id.layout_chuyentien_cardview);
        layoutChuyenTienCardview1 = view.findViewById(R.id.layout_chuyentien_cardview1);
        layoutThanhToanHoaDonCardview = view.findViewById(R.id.layout_thanhtoanhoadon_cardview);
        layoutThanhToanHoaDonCardview1 = view.findViewById(R.id.layout_thanhtoanhoadon_cardview1);
        layoutNapTienCardview = view.findViewById(R.id.layout_naptien_cardview);
        layoutNapTienCardview1 = view.findViewById(R.id.layout_naptien_cardview1);
        layoutDauTuCardview = view.findViewById(R.id.layout_dautu_cardview);
        layoutDauTuCardview1 = view.findViewById(R.id.layout_dautu_cardview1);
        layoutDichVuBaoHiemCardview = view.findViewById(R.id.layout_dichvubaohiem_cardview);
        layoutDichVuBaoHiemCardview1 = view.findViewById(R.id.layout_dichvubaohiem_cardview1);
        layoutMuaSamCardview = view.findViewById(R.id.layout_muasam_cardview);
        layoutMuaSamCardview1 = view.findViewById(R.id.layout_muasam_cardview1);
        layoutTienIchCardview = view.findViewById(R.id.layout_tienich_cardview);
        layoutTienIchCardview1 = view.findViewById(R.id.layout_tienich_cardview1);
        layoutNganSachNhaNuocCardview = view.findViewById(R.id.layout_ngansachnhanuoc_cardview);
        layoutNganSachNhaNuocCardview1 = view.findViewById(R.id.layout_ngansachnhanuoc_cardview1);
        layoutDichVuTheCardview = view.findViewById(R.id.layout_dichvuthe_cardview);
        layoutDichVuTheCardview1 = view.findViewById(R.id.layout_dichvuthe_cardview1);
        layoutTietKiemCardview = view.findViewById(R.id.layout_tietkiem_cardview);
        layoutTietKiemCardview1 = view.findViewById(R.id.layout_tietkiem_cardview1);
        layoutTinDungCardview = view.findViewById(R.id.layout_tindung_cardview);
        layoutTinDungCardview1 = view.findViewById(R.id.layout_tindung_cardview1);
        shimmerFrameLayout = view.findViewById(R.id.shimmer);
    }

    private void xuLyCopy() {
        imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToCopy = tvCopy.getText()
                        .toString();
                ClipboardManager clipboardManager =
                        (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", textToCopy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(requireContext(), "Đã sao chép", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void getPhoto() {
        ApiService.apiService.getPosts()
                .enqueue(new Callback<BaseResponse<List<PostResponse>>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<List<PostResponse>>> call,
                            Response<BaseResponse<List<PostResponse>>> response
                    ) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<PostResponse> postResponses = response.body()
                                    .getData();
                            List<Photo> photos = new ArrayList<>();
                            for (PostResponse postResponse : postResponses) {
                                photos.add(new Photo(postResponse.getThumbnail()));
                                Log.d(
                                        "size",
                                        String.valueOf(postResponse.getThumbnail())
                                );
                            }
                            Log.d("size", String.valueOf(photos.size()));
                            setupViewPager(photos);
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BaseResponse<List<PostResponse>>> call, Throwable t
                    ) {
                        Log.e("E", t.getMessage());
                    }
                });
    }

    private void setupViewPager(List<Photo> photos) {
        ViewPager2 viewPager2 = requireView().findViewById(R.id.view_pager2);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0)
                .setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        PhotpViewPager2Adapter adapter = new PhotpViewPager2Adapter(photos);
        viewPager2.setAdapter(adapter);

        final int autoScrollSpeed = 3000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager2.getCurrentItem();
                int nextItem = currentItem + 1;
                if (nextItem >= adapter.getItemCount()) {
                    nextItem = 0;
                }
                viewPager2.setCurrentItem(nextItem, true);
                handler.postDelayed(this, autoScrollSpeed);
            }
        };

        handler.postDelayed(runnable, autoScrollSpeed);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == adapter.getItemCount() - 1) {
                    viewPager2.setCurrentItem(0);
                }
            }
        });
    }

    private void openFeedbackDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.chuyentiendinuocngoai);
        Button btnDong = dialog.findViewById(R.id.btn_dong);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity =
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvLapYeuCau = dialog.findViewById(R.id.tv_lap_yeu_cau_chuyen_tien);

        tvLapYeuCau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LapYeuCauChuyenTien.class));
            }
        });
        TextView tvQuanLyYeuCau = dialog.findViewById(R.id.tv_quan_ly_yeu_cau_chuyen_tien);
        tvQuanLyYeuCau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), QuanLyYeuCauChuyenTien.class));
            }
        });
        dialog.show();
    }

    private void openFeedbackDialogVNPOST() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.rut_chuyen_tai_vnpost);
        Button btnDong = dialog.findViewById(R.id.btn_dong_vnpost);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity =
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvRut = dialog.findViewById(R.id.tv_rut_vnpost);

        tvRut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RutTienMatTaiVNPOST.class));
            }
        });
        TextView tvChuyen = dialog.findViewById(R.id.tv_chuyen_vnpost);
        tvChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChuyenTienMatTaijVNPOST.class));
            }
        });
        dialog.show();
    }

    private void openFeedbackDialogVCBAutoDebit() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.vcb_auto_debit);
        Button btnDong = dialog.findViewById(R.id.btn_dong_vcb_auto_debit);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity =
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvDangKy = dialog.findViewById(R.id.tv_vcb_auto_debit_dang_ki);

        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DangKyDichVu.class));
            }
        });
        TextView tvNgung = dialog.findViewById(R.id.tv_vcb_auto_debit_huy);
        tvNgung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NgungDichVu.class));
            }
        });
        dialog.show();
    }

    private void openFeedbackDialogDangKiNaptienTuDong() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dang_ky_nap_tien_tu_dong);
        Button btnDong = dialog.findViewById(R.id.btn_dong_nap_tien_tu_dong);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity =
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvDangKy = dialog.findViewById(R.id.tv_dang_ky_nap_tien_tu_dong);

        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DangKyNapTienTuDong.class));
            }
        });
        TextView tvHuyDangKy = dialog.findViewById(R.id.tv_huy_dang_ky_nap_tien_tu_dong);
        tvHuyDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HuyDangKy.class));
            }
        });
        dialog.show();
    }

    private void openFeedbackDialogGiaHanThe() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.gia_han_the);
        Button btnDong = dialog.findViewById(R.id.btn_dong_gia_han_the);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity =
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvTheGhiNo = dialog.findViewById(R.id.tv_the_ghi_no);

        tvTheGhiNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DangKyGiaHanThe.class));
            }
        });
        TextView tvTheTinDung = dialog.findViewById(R.id.tv_the_tin_dung);
        tvTheTinDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DangKyGiaHanTheTinDung.class));
            }
        });
        dialog.show();
    }

    private void openFeedbackDialogPhatHanhChuyenDoiThe() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.phat_hanh_chuyen_doi_the);
        Button btnDong = dialog.findViewById(R.id.btn_dong_phat_hanh_chuyen_doi);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity =
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvPhatHanhTheGhiNo = dialog.findViewById(R.id.tv_phat_hanh_the_ghi_no);
        tvPhatHanhTheGhiNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DangKyPhatHanhTheGhiNo.class));
            }
        });
        TextView tvChuyenDoiCongNgheTheGhiNo =
                dialog.findViewById(R.id.tv_dang_ky_chuyen_doi_cong_nghe);
        tvChuyenDoiCongNgheTheGhiNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DangKyChuyenDoiCongNgheTheGhiNo.class));
            }
        });
        TextView tvPhatHanhTheTinDung = dialog.findViewById(R.id.tv_phat_hanh_the_tin_dung);
        tvPhatHanhTheTinDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DangKyGiaHanTheTinDung.class));
            }
        });
        TextView tvPhatHanhThePhuTheGhiNo =
                dialog.findViewById(R.id.tv_phat_hanh_the_phu_the_ghi_no);
        tvPhatHanhThePhuTheGhiNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PhatHanhThePhuTheGhiNo.class));
            }
        });
        dialog.show();
    }

    private void openFeedbackDialogThanhToanTheTinDung() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.thanh_toan_the_tin_dung);
        Button btnDong = dialog.findViewById(R.id.btn_dong_thanh_toan_the_tin_dung);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity =
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvThanhToanChoChinhMinh = dialog.findViewById(R.id.tv_thanh_toan_cho_chinh_minh);
        tvThanhToanChoChinhMinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThanhToanTheTinDung.class));
            }
        });
        TextView tvThanhToanChoChuTheVCBKhac =
                dialog.findViewById(R.id.tv_thanh_toan_cho_chu_the_vcb_khac);
        tvThanhToanChoChuTheVCBKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThanhToanChoChuTheVCBKhac.class));
            }
        });
        dialog.show();
    }

    private void openFeedbackDialogTatToanKhoanVay() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.tat_toan_khoan_vay);
        Button btnDong = dialog.findViewById(R.id.btn_dong_khoan_vay);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity =
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvVayThauChi = dialog.findViewById(R.id.tv_vay_thau_chi);
        tvVayThauChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VayThauChiTatToan.class));
            }
        });
        TextView tvVayTrucTuyenCamCoTienGui =
                dialog.findViewById(R.id.tv_vay_truc_tuyen_cam_co_tien_gui);
        tvVayTrucTuyenCamCoTienGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VayTrucTuyenCamCoTienGui.class));
            }
        });
        TextView tvTatToanVayKhac = dialog.findViewById(R.id.tv_tat_toan_vay_khac);
        tvTatToanVayKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TatToanVayKhac.class));
            }
        });
        dialog.show();
    }

    private void openFeedbackDialogKhoiTaoKhoanVay() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.khoi_tao_khoan_vay);
        Button btnDong = dialog.findViewById(R.id.btn_dong_khoi_tao);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity =
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvVayThauChi = dialog.findViewById(R.id.tv_vay_thau_chi_khoi_tao);
        tvVayThauChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VayThauChiTatToan.class));
            }
        });
        TextView tvVayTrucTuyenCamCoTienGui = dialog.findViewById(R.id.tv_vay_truc_tuyen_khoi_tao);
        tvVayTrucTuyenCamCoTienGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VayTrucTuyenCamCoTienGui.class));
            }
        });
        dialog.show();
    }

    private void openFeedbackDialogCacDichVuKhacHoaDon() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.cac_dich_vu_khac_cua_thanh_toan_hoa_don);
        Button btnDong = dialog.findViewById(R.id.btn_dong_hoa_don);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity =
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvCuocDienThoaiCoDinh = dialog.findViewById(R.id.tv_cuoc_dien_thoai_co_dinh);
        tvCuocDienThoaiCoDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CuocDienThoaiCoDinh.class));
            }
        });
        TextView tvCuocTruyenHinhCap = dialog.findViewById(R.id.tv_cuoc_truyen_hinh_cap);
        tvCuocTruyenHinhCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CuocTruyenHinhCap.class));
            }
        });
        TextView tvCuocVienThongVNPT = dialog.findViewById(R.id.tv_cuoc_vien_thong_VNPT);
        tvCuocVienThongVNPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HoaDonCuocVienThongVNPT.class));
            }
        });
        TextView tvPhiDichVuChungCu = dialog.findViewById(R.id.tv_phi_dich_vu_chung_cu);
        tvPhiDichVuChungCu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PhiDichVuChungCu.class));
            }
        });
        TextView tvThanhToanKhoanVayTaiChinh =
                dialog.findViewById(R.id.tv_thanh_toan_khoan_vay_tai_chinh);
        tvThanhToanKhoanVayTaiChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThanhToanKhoanVayTaiChinh.class));
            }
        });
        TextView tvThanhToanVeMayBay = dialog.findViewById(R.id.tv_thanh_toan_ve_may_bay);

        TextView tvTienThuePhiDichVuKhac = dialog.findViewById(R.id.tv_tien_thue_phi_dich_vu_khac);
        tvTienThuePhiDichVuKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TienThuePhiDichVuKhac.class));
            }
        });
        dialog.show();
    }

    private void openFeedbackDialogDichVuTheKhac() {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dich_vu_the_khac);
        Button btnDong = dialog.findViewById(R.id.btn_dong_dich_vu_the_khac);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {

            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity =
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvMoKhoaThe = dialog.findViewById(R.id.tv_mo_khoa_the);
        tvMoKhoaThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MoKhoaThe.class));
            }
        });
        TextView tvKhoaThe = dialog.findViewById(R.id.tv_khoa_the);
        tvKhoaThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), KhoaThe.class));
            }
        });
        TextView tvKichHoatThe = dialog.findViewById(R.id.tv_kich_hoat_the);
        tvKichHoatThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), KichHoatThe.class));
            }
        });
        TextView tvDangKySuDungTheTrenInternet =
                dialog.findViewById(R.id.tv_dang_ky_su_dung_the_tren_internet);
        tvDangKySuDungTheTrenInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DangKiSuDungTheTrenInternet.class));
            }
        });
        TextView tvHuyDangKySuDungTheTrenInternet =
                dialog.findViewById(R.id.tv_huy_dang_ky_su_dung_the_tren_internet);
        tvHuyDangKySuDungTheTrenInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HuyDangKySuDungTheTrenInternet.class));
            }
        });
        TextView tvDangKySuDungTheTaiPOSATM =
                dialog.findViewById(R.id.tv_dang_ky_su_dung_the_tai_pos_atm_nuoc_ngoai);
        tvDangKySuDungTheTaiPOSATM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        getActivity(),
                        DangKySuDungTheTaiPOSATMNuocNgoai.class
                ));
            }
        });
        TextView tvHuyDangKySuDungTheTaiPOSATM =
                dialog.findViewById(R.id.tv_huy_dang_ky_su_dung_the_tai_pos_atm_nuoc_ngoai);
        tvHuyDangKySuDungTheTaiPOSATM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        getActivity(),
                        HuyDangKySuDungTheTaiPOSATMNuocNgoai.class
                ));
            }
        });
        TextView tvTaoMoiDoiMaPin = dialog.findViewById(R.id.tv_tao_moi_doi_ma_pin);
        tvTaoMoiDoiMaPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TaoMoiDoiMaPIN.class));
            }
        });
        TextView tvThayDoiHanMucNgayCuaThe =
                dialog.findViewById(R.id.tv_thay_doi_han_muc_ngay_cua_the);
        tvThayDoiHanMucNgayCuaThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThayDoiHanMucNgayCuaThe.class));
            }
        });
        TextView tvTheoDoiHanhTrinhPhatHanhThe =
                dialog.findViewById(R.id.tv_theo_doi_hanh_trinh_phat_hanh_the);
        tvTheoDoiHanhTrinhPhatHanhThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TienThuePhiDichVuKhac.class));
            }
        });
        TextView tvCaiDatHinhThucThanhToanSaoKe =
                dialog.findViewById(R.id.tv_cai_dat_hinh_thuc_thanh_toan_sao_ke);
        tvCaiDatHinhThucThanhToanSaoKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CaiDatHinhThucThanhToanSaoKe.class));
            }
        });
        TextView tvThayDoiTaiKhoanThanhToanChiDinh =
                dialog.findViewById(R.id.tv_thay_doi_tai_khoan_thanh_toan_chi_dinh);
        tvThayDoiTaiKhoanThanhToanChiDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThayDoiTaiKhoanThanhToanChiDinh.class));
            }
        });

        dialog.show();
    }

    private void openFeedbackDialogTraSoatTrucTuyen() {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.loaiyeucautrasoat);
        Button btnDong = dialog.findViewById(R.id.btn_dong_loai_yeu_cau_tra_soat);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {

            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity =
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvLapYeuCauTraSoat = dialog.findViewById(R.id.tv_lap_yeu_cau_tra_soat);
        tvLapYeuCauTraSoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LapYeuCauTraSoat.class));
            }
        });
        TextView tvTraCuuYeuCauTraSoat = dialog.findViewById(R.id.tv_tra_cuu_yeu_cau_tra_soat);
        tvTraCuuYeuCauTraSoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TraCuuYeuCauTraSoat.class));
            }
        });
        dialog.show();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        ImageView imageView = dialogView.findViewById(R.id.img_view_alert);
        imageView.setImageResource(R.drawable.sorry);

        TextView textView = dialogView.findViewById(R.id.tv_alert);
        textView.setText("Tính năng chưa được phát triển. Bạn vẫn muốn tiếp tục chứ?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void airplaneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.ve_may_bay_dialog, null);
        builder.setView(dialogView);

        ImageView imgviewAirplane = dialogView.findViewById(R.id.img_view_airplane);
        imgviewAirplane.setImageResource(R.drawable.baseline_airplane_ticket_24);
        TextView tvAirplane = dialogView.findViewById(R.id.tv_alert_airplane);
        TextView tvAirplane1 = dialogView.findViewById(R.id.tv_alert_airplane1);
        tvAirplane.setText("Đặt vé máy bay");
        tvAirplane1.setText(
                "Đặt vé máy bay là dich vụ do Công ty Cổ phần Giải pháp thanh toán Viêt Nam(VNPAY) cung cấp. Mọi vấn đề liên quan đến hàng hóa dịch vụ sẽ do VNPAY chịu trách nhiệm. VCB đóng vai trò cung cấp giải pháp thanh toán. Trường hợp cần hỗ trợ về dịch vụ này, Quý khách vui lòng liên hệ tổng đài 1900 5555 20");
        builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void vnshopDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.mua_sam_vnshop_dialog, null);
        builder.setView(dialogView);

        ImageView imgviewVNShop = dialogView.findViewById(R.id.img_view_vnshop);
        imgviewVNShop.setImageResource(R.drawable.muasamvnshop);
        TextView tvVNShop = dialogView.findViewById(R.id.tv_alert_vnshop);
        TextView tvVNShop1 = dialogView.findViewById(R.id.tv_alert_vnshop1);
        tvVNShop.setText("Mua sắm VNShop");
        tvVNShop1.setText(
                "Mua sắm VNShop là dich vụ do Công ty Cổ phần Giải pháp thanh toán Viêt Nam(VNPAY) cung cấp. Mọi vấn đề liên quan đến hàng hóa dịch vụ sẽ do VNPAY chịu trách nhiệm. VCB đóng vai trò cung cấp giải pháp thanh toán. Trường hợp cần hỗ trợ về dịch vụ này, Quý khách vui lòng liên hệ tổng đài 1900 5555 20");
        builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void hotelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dat_phong_khach_san, null);
        builder.setView(dialogView);

        ImageView imgviewHotel = dialogView.findViewById(R.id.img_view_hotel);
        imgviewHotel.setImageResource(R.drawable.datphongkhachsan);
        TextView tvHotel = dialogView.findViewById(R.id.tv_alert_hotel);
        TextView tvHotel1 = dialogView.findViewById(R.id.tv_alert_hotel1);
        tvHotel.setText("Đặt phòng khách sạn");
        tvHotel1.setText(
                "Đặt phòng khách sạn là dich vụ do Công ty Cổ phần Giải pháp thanh toán Viêt Nam(VNPAY) cung cấp. Mọi vấn đề liên quan đến hàng hóa dịch vụ sẽ do VNPAY chịu trách nhiệm. VCB đóng vai trò cung cấp giải pháp thanh toán. Trường hợp cần hỗ trợ về dịch vụ này, Quý khách vui lòng liên hệ tổng đài 1900 5555 20");
        builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void phimDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dat_ve_xem_phim, null);
        builder.setView(dialogView);

        ImageView imgviewPhim = dialogView.findViewById(R.id.img_view_phim);
        imgviewPhim.setImageResource(R.drawable.datvexemphim);
        TextView tvPhim = dialogView.findViewById(R.id.tv_alert_phim);
        TextView tvPhim1 = dialogView.findViewById(R.id.tv_alert_phim1);
        tvPhim.setText("Đặt vé xem phim");
        tvPhim1.setText(
                "Đặt vé xem phim là dich vụ do Công ty Cổ phần Giải pháp thanh toán Viêt Nam(VNPAY) cung cấp. Mọi vấn đề liên quan đến hàng hóa dịch vụ sẽ do VNPAY chịu trách nhiệm. VCB đóng vai trò cung cấp giải pháp thanh toán. Trường hợp cần hỗ trợ về dịch vụ này, Quý khách vui lòng liên hệ tổng đài 1900 5555 20");
        builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void xeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dat_ve_xe, null);
        builder.setView(dialogView);

        ImageView imgviewXe = dialogView.findViewById(R.id.img_view_xe);
        imgviewXe.setImageResource(R.drawable.datvexe);
        TextView tvXe = dialogView.findViewById(R.id.tv_alert_xe);
        TextView tvXe1 = dialogView.findViewById(R.id.tv_alert_xe1);
        tvXe.setText("Đặt vé xe");
        tvXe1.setText(
                "Đặt vé xe là dich vụ do Công ty Cổ phần Giải pháp thanh toán Viêt Nam(VNPAY) cung cấp. Mọi vấn đề liên quan đến hàng hóa dịch vụ sẽ do VNPAY chịu trách nhiệm. VCB đóng vai trò cung cấp giải pháp thanh toán. Trường hợp cần hỗ trợ về dịch vụ này, Quý khách vui lòng liên hệ tổng đài 1900 5555 20");
        builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void tauDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dat_ve_tau, null);
        builder.setView(dialogView);

        ImageView imgviewTau = dialogView.findViewById(R.id.img_view_tau);
        imgviewTau.setImageResource(R.drawable.datvetau);
        TextView tvTau = dialogView.findViewById(R.id.tv_alert_tau);
        TextView tvTau1 = dialogView.findViewById(R.id.tv_alert_tau1);
        tvTau.setText("Đặt vé tàu");
        tvTau1.setText(
                "Đặt vé tàu là dich vụ do Công ty Cổ phần Giải pháp thanh toán Viêt Nam(VNPAY) cung cấp. Mọi vấn đề liên quan đến hàng hóa dịch vụ sẽ do VNPAY chịu trách nhiệm. VCB đóng vai trò cung cấp giải pháp thanh toán. Trường hợp cần hỗ trợ về dịch vụ này, Quý khách vui lòng liên hệ tổng đài 1900 5555 20");
        builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void hoaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dat_hoa, null);
        builder.setView(dialogView);

        ImageView imgviewHoa = dialogView.findViewById(R.id.img_view_hoa);
        imgviewHoa.setImageResource(R.drawable.dathoa);
        TextView tvHoa = dialogView.findViewById(R.id.tv_alert_hoa);
        TextView tvHoa1 = dialogView.findViewById(R.id.tv_alert_hoa1);
        tvHoa.setText("Đặt hoa");
        tvHoa1.setText(
                "Đặt hoa là dich vụ do Công ty Cổ phần Giải pháp thanh toán Viêt Nam(VNPAY) cung cấp. Mọi vấn đề liên quan đến hàng hóa dịch vụ sẽ do VNPAY chịu trách nhiệm. VCB đóng vai trò cung cấp giải pháp thanh toán. Trường hợp cần hỗ trợ về dịch vụ này, Quý khách vui lòng liên hệ tổng đài 1900 5555 20");
        builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void taxiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.vnpay_taxi, null);
        builder.setView(dialogView);

        ImageView imgviewTaxi = dialogView.findViewById(R.id.img_view_taxi);
        imgviewTaxi.setImageResource(R.drawable.vnpaytaxi);
        TextView tvTaxi = dialogView.findViewById(R.id.tv_alert_taxi);
        TextView tvTaxi1 = dialogView.findViewById(R.id.tv_alert_taxi1);
        tvTaxi.setText("VNPAY Taxi");
        tvTaxi1.setText(
                "VNPAY Taxi là dich vụ do Công ty Cổ phần Giải pháp thanh toán Viêt Nam(VNPAY) cung cấp. Mọi vấn đề liên quan đến hàng hóa dịch vụ sẽ do VNPAY chịu trách nhiệm. VCB đóng vai trò cung cấp giải pháp thanh toán. Trường hợp cần hỗ trợ về dịch vụ này, Quý khách vui lòng liên hệ tổng đài 1900 5555 20");
        builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openFeedbackDialogDatLichHenVoiVCB() {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dat_lich_hen_voi_vcb);
        Button btnDong = dialog.findViewById(R.id.btn_dong_dat_lich_hen_voi_vcb);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvDatLichHen = dialog.findViewById(R.id.tv_dat_lich_hen);
        tvDatLichHen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DatLichHen.class));
            }
        });
        TextView tvLichSuDatLichHen = dialog.findViewById(R.id.tv_lich_su_dat_lich_hen);
        tvLichSuDatLichHen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LichSuDatLichHen.class));
            }
        });
        dialog.show();
    }

    private void openFeedbackDialogTietKiem() {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.tiet_kiem_thuong);
        Button btnDong = dialog.findViewById(R.id.btn_dong_tiet_kiem_thuong);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {

            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity =
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            window.setAttributes(layoutParams);
        }
        TextView tvMoTietKiem = dialog.findViewById(R.id.tv_mo_tiet_kiem);

        tvMoTietKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MoTietKiem.class));
            }
        });
        TextView tvNopThemVaoTietKiem = dialog.findViewById(R.id.tv_nop_them_vao_tiet_kiem);
        tvNopThemVaoTietKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NopThemVaoTietKiem.class));
            }
        });
        TextView tvRutTienTuTietKiem = dialog.findViewById(R.id.tv_rut_tien_tu_tiet_kiem);
        tvRutTienTuTietKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RutTienTuTietKiem.class));
            }
        });
        TextView tvTatToanTietKiem = dialog.findViewById(R.id.tv_tat_toan_tiet_kiem);
        tvTatToanTietKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TatToanTietKiem.class));
            }
        });
        dialog.show();
    }
}