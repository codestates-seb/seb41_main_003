import styles from './Toggle.module.css';
import PropTypes from 'prop-types';
import { useSetRecoilState, useResetRecoilState, useRecoilValue } from 'recoil';
import ModalState from '../../recoil/modal';
import axios from 'axios';
import Profile from '../../recoil/profile';

const Toggle = ({ user, setUser }) => {
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);
  const { profileId } = useRecoilValue(Profile);

  const confirm = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: '공고 상태를 변경하시겠습니까?',
      modalHandler: () => {
        patchWantedStatus();
        resetModal();
      },
    },
  };

  const patchWantedStatus = async () => {
    await axios
      .patch(`${process.env.REACT_APP_BASE_URL}/profiles/status/${profileId}`, {
        wantedStatus: user.wantedStatus === 'NONE' ? 'REQUEST' : 'NONE',
      })
      .then(({ data }) => {
        console.log(data.data.wantedStatus);
        setUser({
          ...user,
          wantedStatus: data.data.wantedStatus,
        });
      })
      .catch((err) => console.log(err));
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
