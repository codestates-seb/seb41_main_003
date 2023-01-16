import { atom } from 'recoil';

const ModalState = atom({
  key: 'ModalState',
  default: {
    isOpen: false,
    modalType: '',
    props: {},
  },
});

export default ModalState;
