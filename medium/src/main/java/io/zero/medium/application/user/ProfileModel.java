package io.zero.medium.application.user;

import io.zero.medium.domain.user.Profile;
import lombok.Value;

import static java.lang.String.valueOf;

@Value
public class ProfileModel {
    ProfileModelNested profile;

    public static ProfileModel fromProfile(Profile profile) {
        return new ProfileModel(ProfileModelNested.fromProfile(profile));
    }

    @Value
    public static class ProfileModelNested {
        String username;
        String bio;
        String image;
        boolean following;

        public static ProfileModelNested fromProfile(Profile profile) {
            return new ProfileModelNested(valueOf(profile.getUserName()),
                    profile.getBio(),
                    valueOf(profile.getImage()),
                    profile.isFollowing());
        }
    }
}
