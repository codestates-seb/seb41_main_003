import styles from './DatePickerForm.module.css';
import { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import PropTypes from 'prop-types';
import { VscTriangleDown } from 'react-icons/vsc';
import './react-datepicker.css'; //기본 CSS 파일
import './DatePicker.css'; // Custom CSS 파일

const DatePickerForm = ({ user, setUser }) => {
  //"1월 10일 09:00" or "Thu Jan 05 2023 22:20:58"\

  const [startDate, setStartDate] = useState(new Date());
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [day, setDay] = useState(new Date().getDate());

  useEffect(() => {
    // console.log('날짜 변경이 반영되어서 랜더');
  }, startDate);

  const getPickedDate = (date) => {
    setYear(date.getFullYear());
    setDay(('0' + date.getDate()).slice(-2));
    setMonth(('0' + (date.getMonth() + 1)).slice(-2));
  };

  const DateCustomButton = ({ onClick }) => (
    <button className={styles.iconButton} onClick={onClick}>
      <VscTriangleDown size="24px" />
    </button>
  );

  //실제로 데이터 바꿔주는 함수
  const userEditHandler = () => {
    setUser({ ...user, startTime: 'Change to', endTime: 'Change to' });
    console.log('startTime이 변경되었습니다');
  };

  // TODO: startTime = 날짜 + 시작시간
  // TODO: endTime = 날짜 + 끝시간

  return (
    <div className={styles.container}>
      <div className={styles.line1}>
        <p className={styles.font1}>{day}</p>
        <DatePicker
          showPopperArrow={false}
          className={styles.calendar}
          onChange={(date) => setStartDate(date)}
          onSelect={getPickedDate}
          selected={startDate}
          dateFormat="h:mm aa" //"h:mm aa"
          customInput={<DateCustomButton />}
        />
      </div>
      <div className={styles.line2}>
        <p className={styles.font4}>
          {year}년 {month}월
        </p>
      </div>
      <div className={styles.line3}>
        <div className={styles.font6}>09:00~18:00</div>
        <div></div>
      </div>
    </div>
  );
};
DatePickerForm.propTypes = {
  onClick: PropTypes.func,
  user: PropTypes.object,
  setUser: PropTypes.func,
};
export default DatePickerForm;
