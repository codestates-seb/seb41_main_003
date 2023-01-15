import styles from './Toggle.module.css';
import PropTypes from 'prop-types';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal';

const Toggle = ({ user, setUser }) => {
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const confirm = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: '공고 상태를 변경하시겠습니까?',
      modalHandler: () => {
        setUser({
          ...user,
          wantedStatus: user.wantedStatus === 'NONE' ? 'REQUEST' : 'NONE',
        });
        console.log('공고상태 변경 완료');
        resetModal();
      },
    },
  };

  return (
    <div className={styles.toggleSwitch}>
      <button
        onClick={(e) => {
          e.preventDefault();
          setModal(confirm);
        }}
      >
        <input
          className={styles.checkbox}
          type="checkbox"
          defaultChecked={user.wantedStatus === 'REQUEST' ? true : false}
        />
        <span
          className={`${styles.slider} ${
            user.wantedStatus === 'REQUEST' ? '' : styles.unActive
          }`}
        ></span>
      </button>
    </div>
  );
};

Toggle.propTypes = {
  user: PropTypes.object,
  setUser: PropTypes.func,
};

export default Toggle;
