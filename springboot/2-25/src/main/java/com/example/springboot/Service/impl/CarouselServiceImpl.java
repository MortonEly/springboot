package com.example.springboot.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Mapper.CarouseMapper;
import com.example.springboot.Service.ICarouselService;
import com.example.springboot.entity.Carousel;
import org.springframework.stereotype.Service;

/**
 * @Auther: 吕宏博
 * @Date: 2024--03--01--10:07
 * @Description:
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouseMapper, Carousel> implements ICarouselService {
}
