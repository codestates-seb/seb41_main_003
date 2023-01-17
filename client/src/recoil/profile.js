import { atom } from 'recoil';

const localStorageEffect =
  (key) =>
  ({ setSelf, onSet }) => {
    const savedValue = localStorage.getItem(key);
    if (savedValue != null) {
      setSelf(JSON.parse(savedValue));
    }

    onSet((newValue, _, isReset) => {
      isReset
        ? localStorage.removeItem(key)
        : localStorage.setItem(key, JSON.stringify(newValue));
    });
  };

const Profile = atom({
  key: 'Profile',
  default: {
    isLogin: false,
    profileId: 0,
    userStatus: '',
  },
  effects: [localStorageEffect('current_user')],
});

export default Profile;
