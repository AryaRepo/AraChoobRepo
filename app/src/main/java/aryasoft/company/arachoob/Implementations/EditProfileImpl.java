package aryasoft.company.arachoob.Implementations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileImpl implements Callback<Boolean> {

    private OnProfileEditedStatusListener ProfileEditedListener;

    public EditProfileImpl(OnProfileEditedStatusListener profileEdited) {
        ProfileEditedListener = profileEdited;
    }

    @Override
    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
        ProfileEditedListener.onProfileEdited(response);
    }

    @Override
    public void onFailure(Call<Boolean> call, Throwable t) {

    }


    public interface OnProfileEditedStatusListener
    {
        void onProfileEdited(Response<Boolean> response);
    }

}
