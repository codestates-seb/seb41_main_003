import styles from './ImgLoadModal.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue, ButtonSilver, ButtonRed } from '../Button';
import { useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';
import { useState } from 'react';
import axios from 'axios';

const ImgLoadModal = ({ setUser, profileId }) => {
  const [imgName, setImgName] = useState('');
  const [imgFile, setImgFile] = useState({});
  const reset = useResetRecoilState(ModalState);

  const changeHandler = (e) => {
    console.log(e.target.files[0]);
    if (e.target.files[0].size >= 5 * 1024 * 1024) {
      alert('파일의 크기는 최대 5MB입니다.');
      return;
    }
    if (e.target.files) {
      setImgFile(e.target.files[0]);
      setImgName(e.target.value);
    }
  };

  const submitHandler = (file) => {
    const formData = new FormData();
    formData.append('image', file);
    for (const key of formData.keys()) {
      console.log(key);
    }
    for (const value of formData.values()) {
      console.log(value);
    }
    patchImg(formData);
    reset();
  };

  const patchImg = async (formData) => {
    await axios
      .patch(
        `${process.env.REACT_APP_BASE_URL}/upload/profile-image/${profileId}`,
        formData,
        {
          headers: {
            Authorization:
              sessionStorage.getItem('authorization') ||
              localStorage.getItem('authorization'),
            'Content-Type': 'multipart/form-data',
          },
        }
      )
      .then(({ data }) => {
        console.log(data.data[0]);
        console.log('성공!');

        setUser((prev) => ({
          ...prev,
          profileImage: data.data[0],
        }));
      })
      .catch((err) => console.log(err));
  };

  const deleteHandler = () => {
    setImgName('');
    deleteImg();
    reset();
  };

  const deleteImg = async () => {
    await axios
      .delete(
        `${process.env.REACT_APP_BASE_URL}/upload/profile-image/${profileId}`,
        {
          headers: {
            Authorization:
              sessionStorage.getItem('authorization') ||
              localStorage.getItem('authorization'),
          },
        }
      )
      .then(() => {
        console.log('프로필 이미지 삭제 성공!');
        setUser((prev) => ({
          ...prev,
          profileImage: {},
        }));
      })
      .catch((err) => console.log(err));
  };

  return (
    <div
      className={styles.view}
      onClick={(e) => e.stopPropagation()}
      role="dialog"
      aria-hidden
    >
      <div className={styles.text}>프로필 이미지를 업로드 해주세요.</div>
      <form className={styles.fileBox}>
        <input placeholder="프로필 이미지" value={imgName} readOnly={true} />
        <label htmlFor="file">파일 찾기</label>
        <input
          className={styles.fileInput}
          type="file"
          id="file"
          accept="image/jpg, image/jpeg, image/png"
          name="profile_loader"
          multiple={false}
          onChange={changeHandler}
        ></input>
      </form>
      <div className={styles.buttonBox}>
        <ButtonNightBlue
          buttonHandler={() => {
            submitHandler(imgFile);
          }}
          text="저장"
        />
        <ButtonRed buttonHandler={deleteHandler} text="삭제" />
        <ButtonSilver buttonHandler={() => reset()} text="취소" />
      </div>
    </div>
  );
};

ImgLoadModal.propTypes = {
  setUser: PropTypes.func,
  profileId: PropTypes.string,
};

export default ImgLoadModal;
