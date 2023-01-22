import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

// key: 저장소에 저장되는 key 값
// setSelf: 연결된 atom 의 값을 초기화 해주는 함수
// onSet: 해당하는 atom 의 값이 변경이 되었을 때 실행되는 함수
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

const CurrentRoomIdState = atom({
  key: 'CurrentRoomId',
  default: 0,
  effects: [localStorageEffect('current_room_id')],
  effects_UNSTABLE: [persistAtom],
});

export default CurrentRoomIdState;
