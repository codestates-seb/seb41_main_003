import styles from './ImgLoadModal.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue, ButtonSilver, ButtonRed } from '../Button';
import { useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';
import { useState } from 'react';

const ImgLoadModal = ({ setImgFile, setImgSrc }) => {
  const [imgName, setImgName] = useState('');
  const [imgBlob, setImgBlob] = useState({});
  const reset = useResetRecoilState(ModalState);

  const changeHandler = (e) => {
    if (e.target.files[0].size >= 5 * 1024 * 1024) {
      alert('파일의 크기는 최대 5MB입니다.');
      return;
    }
    if (e.target.files) {
      setImgFile(e.target.files[0]);
      setImgBlob(e.target.files[0]);
      setImgName(e.target.value);
    }
  };

  const saveHandler = (file) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      setImgSrc(reader.result);
    };
  };

  const deleteHandler = () => {
    setImgName('');
    setImgFile({});
    setImgBlob({});
    setImgSrc();
    reset();
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
            saveHandler(imgBlob);
            reset();
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
  setImgFile: PropTypes.func,
  setImgSrc: PropTypes.func,
};

export default ImgLoadModal;
