import { atom } from 'recoil';

const ModalState = atom({
  key: 'ModalState',
  default: {
    isOpen: false,
    modalType: '',
    backDropHandle: true,
    props: {},
  },
});

export default ModalState;
