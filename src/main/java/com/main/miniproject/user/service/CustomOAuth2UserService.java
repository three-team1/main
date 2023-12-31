package com.main.miniproject.user.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.main.miniproject.user.entity.Role;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService{

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("로그인 정보 " + oAuth2User.getAttributes());

    
        
        // OAuth2User를 기반으로 필요한 정보를 추출하고, User 엔티티를 생성하거나 찾습니다.
        String provider = userRequest.getClientRegistration().getRegistrationId(); // provider 정보
        String providerId = oAuth2User.getName(); // provider에서의 사용자 ID

        User user = userRepository.findByProviderIdAndProvider(providerId, provider)
                .orElseGet(() -> {
                    // 처음 로그인하는 사용자라면 새 User 엔티티를 생성합니다.
                    User newUser = new User();
                    newUser.setProvider(provider);
                    newUser.setProviderId(providerId);
                    newUser.setRole(Role.USER);
                    
                    if (provider.equals("kakao")) {
                        //카카오의 응답형태는 JSON이기에 Map형식으로 받아야한다.
                        Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttribute("properties");

                        //JSON 으로 변환된 properties를 다시 String으로 변환
                        String nickname = (String) properties.get("nickname");    
                        String profileImage = (String) properties.get("profile_image");          
                        newUser.setProfileImage(profileImage);
                        newUser.setUsername(nickname);
                        
                    } else if (provider.equals("google")) {
                        // 구글 사용자의 별명을 얻습니다. 'name' 속성이 존재한다면 그것을 사용합니다.
                    	
                    	String email = (String) oAuth2User.getAttributes().get("email");
                        String nickname = (String) oAuth2User.getAttributes().get("name");
                        String profileImage = (String) oAuth2User.getAttributes().get("picture");
                        newUser.setUsername(nickname);
                        newUser.setEmail(email);
                        newUser.setProfileImage(profileImage);
                    } else if (provider.equals("naver")) {
                    	
                    	 Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
                    	 String email = (String) response.get("email");
                    	 String nickname = (String) response.get("name");
                    	 String phoneNumber = (String) response.get("mobile"); 
                    	 
                    	 newUser.setUsername(nickname);
                    	 newUser.setEmail(email);
                    	 newUser.setTel(phoneNumber);
                    	 
                    	
                    }

                    System.out.println("Saved user: " + newUser);

                    // 기타 필요한 정보를 설정하고 저장합니다.
                    // newUser.set...
                    return userRepository.save(newUser);
                });

        // User 엔티티를 기반으로 세션에 저장할 사용자 정보를 생성합니다.
        UserDetail userDetail = new UserDetail(user, oAuth2User.getAttributes());

        // OAuth2User 객체 반환
        return userDetail;
    }


	
}
