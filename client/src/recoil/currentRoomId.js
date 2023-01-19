import { atom } from 'recoil';

const CurrentRoomIdState = atom({
  key: 'CurrentRoomId',
  default: 0,
});

export default CurrentRoomIdState;
