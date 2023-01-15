import styles from './DatePickerForm.module.css';
import { useState } from 'react';
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

  const userEditHandler = () => {
    setUser({ ...user, startTime: '', endTime: '' });
    console.log('startTime이 변경되었습니다');
    // TODO: startTime = 날짜 + 시작시간
    // TODO: endTime = 날짜 + 끝시간
  };

  return (
    <div className={styles.container}>
      <div className={styles.line1}>
        <p className={styles.font1}>{day}</p>
        <DatePicker
          showPopperArrow={false}
          onChange={(data) => setStartDate(data)}
          onSelect={getPickedDate}
          selected={startDate}
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
        {/* TODO: TimePicker */}
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
