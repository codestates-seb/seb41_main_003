import FeedItem from '../components/MainSection/FeedItem';
import styles from '../pages/TutorList.module.css';
import { MdSearch, MdFilterList } from 'react-icons/md';
import { useState, useEffect, useRef } from 'react';
import FilterDropdown from '../components/MainSection/FilterDropdown';
import { Link } from 'react-router-dom';
import { ButtonTop } from '../components/Button';
import MenuButtons from '../components/MainSection/FilterButton';
import axios from 'axios';
import useScroll from '../util/useScroll';
import LoadingIndicator from '../components/LoadingIndicator';

const TutorList = () => {
  // API에서 받아온 데이터
  const [tutorData, setTutorData] = useState([]);
  const [pageInfo, setPageInfo] = useState({
    page: 1,
  });
  //정렬 메뉴 오픈 상태
  const [isOpen, setIsOpen] = useState(false);
  //과목 필터 메뉴에서 선택한 과목들
  const [subjectMenu, setSubjectMenu] = useState([]);
  //검색창에서 입력한 검색어 반영
  const [search, setSearch] = useState('');
  //검색창 값 핸들링 상태
  const [searchValue, setSearchValue] = useState('');
  //정렬 메뉴 '최신 순'인지 '별점 순'인지
  const [sort, setSort] = useState('');

  const loadingRef = useRef(null);

  const [isLoading, setIsLoading] = useScroll(() => {
    if (pageInfo.page < pageInfo.totalPages - 1) {
      console.log('true');
      setTimeout(() => {
        scrollFunc(pageInfo.page + 1);
        setIsLoading(false);
      }, 500);
    } else setIsLoading(false);
  }, loadingRef);

  const scrollFunc = async (page) => {
    await axios
      .get(
        `/users/tutees?subject=${subjectMenu.join()}&search=${search}&sort=${sort}&page=${page}`
      )
      .then(({ data }) => {
        console.log(data.pageInfo);
        setTutorData([...tutorData, ...data.data]);
        setPageInfo(data.pageInfo);
      })
      .catch((err) => console.error(err.message));
  };

  const getTutorData = async () => {
    await axios
      .get(
        `/users/tutors?subject=${subjectMenu.join()}&search=${search}&sort=${sort}`
      )
      .then(({ data }) => {
        setTutorData(data.data);
        setPageInfo(data.pageInfo);
      })
      .catch((err) => console.error(err.message));
  };

  //정렬 메뉴 오픈 핸들러
  const filterHandler = () => {
    setIsOpen(!isOpen);
  };

  const subjectHandler = (e) => {
    if (subjectMenu.includes(e.target.value)) {
      setSubjectMenu(subjectMenu.filter((el) => el !== e.target.value));
    } else {
      setSubjectMenu([e.target.value, ...subjectMenu]);
    }
  };

  const searchHandler = ({ type, key, target }) => {
    if (type === 'change') setSearchValue(target.value);
    else if (target.value === '') setSearch(searchValue);
    else if (type === 'keyup' && key === 'Enter') setSearch(searchValue);
  };

  useEffect(() => {
    getTutorData();
  }, [subjectMenu, search, sort]);

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <div className={styles.banner}>
          <div className={styles.bannerContents}>
            <span className={styles.bannerText}>
              나에게 꼭 맞는 비대면 과외를 찾아보세요!
            </span>
            <div className={styles.searchBox}>
              <div className={styles.iconBox}>
                <MdSearch className={styles.mdSearch} />
              </div>
              <input
                className={styles.input}
                placeholder="서울대학교"
                value={searchValue}
                onChange={searchHandler}
                onKeyUp={searchHandler}
              ></input>
            </div>
          </div>
        </div>
        <div className={styles.menuContainer}>
          <MenuButtons
            subjectMenu={subjectMenu}
            subjectHandler={subjectHandler}
          />
          <div
            className={styles.filter}
            onClick={filterHandler}
            aria-hidden="true"
          >
            <MdFilterList className={styles.mdFilterList} />
            <span>{sort === 'rate' ? '평점 순' : '최신 순'}</span>
            {isOpen ? <FilterDropdown setFilter={setSort} /> : null}
          </div>
        </div>
        <div className={styles.feedContainer}>
          {tutorData.map((tutor) => (
            <Link
              to={`/tutorprofile`}
              state={{ tutorProfileId: tutor.profileId }}
              key={tutor.profileId}
              className={styles.list}
            >
              <FeedItem data={tutor} userStatus="TUTOR" />
            </Link>
          ))}
          {tutorData.length === 0 && (
            <div className={styles.notFound}>
              표시 할 튜터가 없습니다. 검색 조건을 확인하세요.
            </div>
          )}
        </div>
        <LoadingIndicator ref={loadingRef} isLoading={isLoading} />
      </div>
      <ButtonTop />
    </div>
  );
};

export default TutorList;
